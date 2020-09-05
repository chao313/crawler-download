package demo.spring.boot.demospringboot.controller.wind.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Header;

import java.util.concurrent.ExecutionException;


/**
 * 同步发送
 *
 * @return
 */

@Slf4j
public class KafkaProduceSendSyncService<K, V> extends KafkaProduceService {


    /**
     * 获取实例 ( 不对外开放，由工厂来获取 )
     * {@link ProduceFactory#getKafkaProduceSendSyncService()}
     */
    protected static <K, V> KafkaProduceSendSyncService<K, V> getInstance(KafkaProducer kafkaProducer) {
        return new KafkaProduceSendSyncService(kafkaProducer);
    }


    private KafkaProduceSendSyncService(KafkaProducer kafkaProducer) {
        super(kafkaProducer);
    }

    /**
     * 同步 - 底层()
     */
    public RecordMetadataResponse sendSync(ProducerRecord<K, V> producerRecord) throws ExecutionException, InterruptedException {
        RecordMetadata recordMetadata = (RecordMetadata) super.kafkaProducer.send(producerRecord).get();
        return new RecordMetadataResponse(recordMetadata);
    }

    /**
     * 这个是最全的 全部的的发送都要调用这个
     *
     * @param topic     指定 topic
     * @param partition 指定 分区
     * @param timestamp 指定 时间戳
     * @param key       指定 key
     * @param value     指定 value
     * @param headers   指定 headers
     * @return :
     * {
     * "offset": 46,
     * "timestamp": 1585982277534,
     * "serializedKeySize": 5,
     * "serializedValueSize": 5,
     * "partition": 0,
     * "topic": "Topic11"
     * }
     */

    public RecordMetadataResponse sendSync(String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers) throws ExecutionException, InterruptedException {
        ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, timestamp, key, value, headers);
        return this.sendSync(record);
    }

    /**
     * 这里忽略了 headers
     */
    public RecordMetadataResponse sendSync(String topic, Integer partition, Long timestamp, K key, V value) throws ExecutionException, InterruptedException {
        return this.sendSync(topic, partition, timestamp, key, value, null);
    }

    /**
     * 这里忽略了 headers 和 timestamp
     */
    public RecordMetadataResponse sendSync(String topic, Integer partition, K key, V value) throws ExecutionException, InterruptedException {
        return this.sendSync(topic, partition, null, key, value, null);
    }

    /**
     * 这里忽略了 headers 和 timestamp 和 partition
     */
    public RecordMetadataResponse sendSync(String topic, K key, V value) throws ExecutionException, InterruptedException {
        return this.sendSync(topic, null, null, key, value, null);
    }

    /**
     * 这个是最少的需要 topic 和 value
     *
     * @param topic
     * @param value
     */
    public RecordMetadataResponse sendSync(String topic, V value) throws ExecutionException, InterruptedException {
        return this.sendSync(topic, null, null, null, value, null);
    }


    @Override
    public void sendProducerRecord(ProducerRecord record) throws ExecutionException, InterruptedException {
        this.sendSync(record);
    }
}
