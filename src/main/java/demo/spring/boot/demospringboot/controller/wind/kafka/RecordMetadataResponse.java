package demo.spring.boot.demospringboot.controller.wind.kafka;

import lombok.Data;
import org.apache.kafka.clients.producer.RecordMetadata;

/**
 * 为了返回前端而存在的
 */
@Data
public class RecordMetadataResponse {

    /**
     * 偏移量
     */
    private long offset;
    // The timestamp of the message.
    // If LogAppendTime is used for the topic, the timestamp will be the timestamp returned by the broker.
    // If CreateTime is used for the topic, the timestamp is the timestamp in the corresponding ProducerRecord if the
    // user provided one. Otherwise, it will be the producer local time when the producer record was handed to the
    // producer.
    /**
     * 消息的时间戳 可以是消息的创建时间 ， 也可以是消息的接收时间
     */
    private long timestamp;
    /**
     * 序列化后的 key size
     */
    private int serializedKeySize;
    /**
     * 序列化后的 value size
     */
    private int serializedValueSize;

    /**
     * partition
     */
    private int partition;
    private String topic;

    public RecordMetadataResponse(RecordMetadata recordMetadata) {
        partition = recordMetadata.partition();
        topic = recordMetadata.topic();

        serializedValueSize = recordMetadata.serializedValueSize();
        serializedKeySize = recordMetadata.serializedKeySize();
        timestamp = recordMetadata.timestamp();
        offset = recordMetadata.offset();

    }


}
