package demo.spring.boot.demospringboot.controller.wind.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
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
public class KafkaProduceSendAsyncService<K, V> extends KafkaProduceService {


    /**
     * 获取实例 ( 不对外开放，由工厂来获取 )
     * {@link ProduceFactory#getKafkaProduceDefaultService()}
     */
    protected static <K, V> KafkaProduceSendAsyncService<K, V> getInstance(KafkaProducer kafkaProducer) {
        return new KafkaProduceSendAsyncService(kafkaProducer);
    }


    private KafkaProduceSendAsyncService(KafkaProducer kafkaProducer) {
        super(kafkaProducer);
    }

    /**
     * 异步 - 底层()
     */
    public void sendAsync(ProducerRecord<K, V> producerRecord, Callback callback) {
        super.kafkaProducer.send(producerRecord, callback);
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
     */

    public void sendAsync(String topic, Integer partition, Long timestamp, K key, V value, Iterable<Header> headers, Callback callback) throws ExecutionException, InterruptedException {
        ProducerRecord<K, V> record = new ProducerRecord<>(topic, partition, timestamp, key, value, headers);
        this.sendAsync(record, callback);
    }

    /**
     * 这里忽略了 headers
     */
    public void sendAsync(String topic, Integer partition, Long timestamp, K key, V value, Callback callback) throws ExecutionException, InterruptedException {
        this.sendAsync(topic, partition, timestamp, key, value, null, callback);
    }

    /**
     * 这里忽略了 headers 和 timestamp
     */
    public void sendAsync(String topic, Integer partition, K key, V value, Callback callback) throws ExecutionException, InterruptedException {
        this.sendAsync(topic, partition, null, key, value, null, callback);
    }

    /**
     * 这里忽略了 headers 和 timestamp 和 partition
     */
    public void sendAsync(String topic, K key, V value, Callback callback) throws ExecutionException, InterruptedException {
        this.sendAsync(topic, null, null, key, value, null, callback);
    }

    /**
     * 这个是最少的需要 topic 和 value
     *
     * @param topic
     * @param value
     */
    public void sendAsync(String topic, V value, Callback callback) throws ExecutionException, InterruptedException {
        this.sendAsync(topic, null, null, null, value, null, callback);
    }


    @Override
    public void sendProducerRecord(ProducerRecord record) throws ExecutionException, InterruptedException {
        this.sendAsync(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                RecordMetadataResponse recordMetadataResponse = new RecordMetadataResponse(metadata);
                log.info("回调成功:recordMetadataResponse:{}", recordMetadataResponse);
                log.info("当前 Msg 的偏移量:{}", recordMetadataResponse.getOffset());
                log.info("当前 Msg 的Partition:{}", recordMetadataResponse.getPartition());
                log.info("当前 Mag 的key的序列号的size:{}", recordMetadataResponse.getSerializedKeySize());
                log.info("当前 Mag 的value的序列号的size:{}", recordMetadataResponse.getSerializedValueSize());
                log.info("当前 Mag 的时间戳:{}", recordMetadataResponse.getTimestamp());
            }
        });
    }
}
