package message.serialization.quick;

import message.*;
import message.serialization.MessageSerializer;
import message.serialization.SerializationException;
import message.serialization.quick.message.*;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.*;
import java.util.HashMap;

public class QuickMessageSerializer implements MessageSerializer {
    BidiMap<Class<? extends Message>, Integer> messageIds = new DualHashBidiMap<>() {{
        put(ErrorMessage.class, 1);
        put(FileListRequest.class, 2);
        put(FileListResponse.class, 3);
        put(FileUploadDone.class, 4);
        put(FileUploadRequest.class, 5);
        put(FileUploadResponse.class, 6);
        put(LoginRequest.class, 7);
        put(LoginResponse.class, 8);
    }};
    private final StateDeserializerMap stateSerializers = new StateDeserializerMap() {{
        put(ErrorMessage.class, new ErrorMessageStateSerializer());
        put(FileListRequest.class, new FileListRequestStateSerializer());
        put(FileListResponse.class, new FileListResponseStateSerializer());
        put(FileUploadDone.class, new FileUploadDoneStateSerializer());
        put(FileUploadRequest.class, new FileUploadRequestStateSerializer());
        put(FileUploadResponse.class, new FileUploadResponseStateSerializer());
        put(LoginRequest.class, new LoginRequestStateSerializer());
        put(LoginResponse.class, new LoginResponseStateSerializer());
    }};

    @Override
    public byte[] serialize(Message message) {
        try {
            var messageStateSerializer = stateSerializers.get(message.getClass());
            if (messageStateSerializer == null) {
                throw new SerializationException("no serializer for message class " + message.getClass());
            }
            var bytesStream = new ByteArrayOutputStream();
            var dataStream = new DataOutputStream(bytesStream);
            var id = messageIds.get(message.getClass());
            if (id == null) {
                throw new SerializationException("internal error: no id for class " + message.getClass());
            }
            dataStream.writeInt(id);
            messageStateSerializer.serialize(message, dataStream); // it' OK, StateDeserializerMap cares
            return bytesStream.toByteArray();
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    @Override
    public Message deserialize(byte[] bytes) {
        try {
            var dataStream = new DataInputStream(new ByteArrayInputStream(bytes));
            var messageId = dataStream.readInt();
            var messageClass = messageIds.getKey(messageId);
            if (messageClass == null) {
                throw new SerializationException("no message class for id " + messageId);
            }
            var messageDeserializer = stateSerializers.get(messageClass);
            if (messageDeserializer == null) {
                throw new SerializationException("no message deserializer for class " + messageClass);
            }
            return messageDeserializer.deserialize(dataStream);
        } catch (IOException e) {
            throw new SerializationException(e.getMessage());
        }
    }

    static class StateDeserializerMap {
        private final HashMap<Class<? extends Message>, MessageStateSerializer> map = new HashMap<>();

        public <T extends Message> void put(Class<T> clazz, MessageStateSerializer<T> serializer) {
            map.put(clazz, serializer);
        }

        public <T extends Message> MessageStateSerializer get(Class<T> clazz) {
            return map.get(clazz);
        }

    }
}
