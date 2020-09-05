package demo.spring.boot.demospringboot.controller.wind.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Map;
import java.util.Properties;

/**
 * 统一对外的ProduceService
 */
public class ProduceFactory<K, V> {

    private KafkaProducer<K, V> kvKafkaProducer;

    private ProduceFactory() {
    }

    private ProduceFactory(KafkaProducer<K, V> kvKafkaProducer) {
        this.kvKafkaProducer = kvKafkaProducer;
    }

    /**
     * 获取  KafkaProduceDefaultService 默认的
     */
    public <K, V> KafkaProduceDefaultService<K, V> getKafkaProduceDefaultService() {
        return KafkaProduceDefaultService.getInstance(this.kvKafkaProducer);
    }


    /**
     * 获取 异步发送 KafkaProduceSendAsyncService
     */
    public <K, V> KafkaProduceSendAsyncService<K, V> getKafkaProduceSendAsyncService() {
        return KafkaProduceSendAsyncService.getInstance(this.kvKafkaProducer);
    }

    /**
     * 获取 发送就忘记  KafkaProduceSendForgetService
     */
    public <K, V> KafkaProduceSendForgetService<K, V> getKafkaProduceSendForgetService() {
        return KafkaProduceSendForgetService.getInstance(this.kvKafkaProducer);
    }

    /**
     * 获取 同步发送 KafkaProduceSendSyncService
     */
    public <K, V> KafkaProduceSendSyncService<K, V> getKafkaProduceSendSyncService() {
        return KafkaProduceSendSyncService.getInstance(this.kvKafkaProducer);
    }


    /**
     * 构造函数(使用默认的方式 除了 bootstrap_servers)
     */
    public static <K, V> ProduceFactory<K, V> getProducerInstance(String bootstrap_servers) {
        Properties kafkaProps = new Properties(); //新建一个Properties对象
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");//key准备是String -> 使用了内置的StringSerializer
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");//value准备是String -> 使用了内置的StringSerializer
        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(kafkaProps);//创建生产者
        return new ProduceFactory(kafkaProducer);
    }

    /**
     * 构造函数(使用默认的方式 除了 bootstrap_servers)(这里添加了覆盖属性的方式)
     */
    public static <K, V> ProduceFactory<K, V> getProducerInstance(String bootstrap_servers, Map<String, String> mapOver) {
        Properties kafkaProps = new Properties(); //新建一个Properties对象
        kafkaProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrap_servers);
        kafkaProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");//key准备是String -> 使用了内置的StringSerializer
        kafkaProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");//value准备是String -> 使用了内置的StringSerializer
        kafkaProps.putAll(mapOver);
        KafkaProducer kafkaProducer = new KafkaProducer<String, String>(kafkaProps);//创建生产者
        return new ProduceFactory(kafkaProducer);
    }


}
