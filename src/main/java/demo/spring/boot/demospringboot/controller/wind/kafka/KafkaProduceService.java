package demo.spring.boot.demospringboot.controller.wind.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Metric;
import org.apache.kafka.common.MetricName;
import org.apache.kafka.common.PartitionInfo;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
public abstract class KafkaProduceService<K, V> {


    private KafkaProduceService() {

    }

    KafkaProduceService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public KafkaProducer getKafkaProducer() {
        return kafkaProducer;
    }

    /**
     * 用于设置kafka的produce
     */
    KafkaProducer kafkaProducer;


    /**
     * Get the partition metadata for the given topic. This can be used for custom partitioning.
     * 获取 partition 的元数据，可以用于自定义的分区
     * 首领分区在的节点  : partitionInfo.leader
     * 当前分区的    id : partitionInfo.partition
     * 当前分区的 Topic : partitionInfo.topic
     * 同步复制子集     : partitionInfo.inSyncReplicas
     * 完整子集        : partitionInfo.replicas
     * 离线副本        : partitionInfo.offlineReplicas
     *
     * @param topic
     * @return
     */
    public List<PartitionInfo> getPartitionsByTopic(String topic) {
        List<PartitionInfo> list = this.kafkaProducer.partitionsFor(topic);
        return list;
    }


    /**
     * 事务提交(需要)
     */
    public void transactionSend(List<ProducerRecord<K, V>> recordList) {
        try {
            this.kafkaProducer.initTransactions();
            this.kafkaProducer.beginTransaction();
            for (ProducerRecord<K, V> record : recordList) {
                this.sendProducerRecord(record);
            }
            this.kafkaProducer.commitTransaction();
        } catch (Exception e) {
            log.info("事务异常，回滚:{}", e.toString(), e);
            this.kafkaProducer.abortTransaction();
        }
    }

    /**
     * 留作子类实现来完成 处理
     *
     * @param record
     */
    public abstract void sendProducerRecord(ProducerRecord<K, V> record) throws ExecutionException, InterruptedException;

    /**
     * get the full set of internal metrics maintained by the producer.
     * <p>
     * groupName:kafka-metrics-count
     * groupName:kafka-metrics-count -> metricName:count -> value:81.0
     * groupName:producer-metrics
     * groupName:producer-metrics -> metricName:successful-authentication-total -> value:0.0
     * groupName:producer-metrics -> metricName:failed-authentication-total -> value:0.0
     * groupName:producer-metrics -> metricName:io-wait-ratio -> value:0.06405285571453703
     * groupName:producer-metrics -> metricName:successful-authentication-no-reauth-total -> val
     * groupName:producer-metrics -> metricName:outgoing-byte-rate -> value:1.6134710161933818
     * groupName:producer-metrics -> metricName:failed-reauthentication-total -> value:0.0
     * groupName:producer-metrics -> metricName:incoming-byte-rate -> value:11.240901620098613
     * groupName:producer-metrics -> metricName:record-queue-time-avg -> value:NaN
     * groupName:producer-metrics -> metricName:request-latency-max -> value:NaN
     * groupName:producer-metrics -> metricName:request-rate -> value:0.058669952183988974
     * groupName:producer-metrics -> metricName:failed-reauthentication-rate -> value:0.0
     * groupName:producer-metrics -> metricName:connection-count -> value:1.0
     * groupName:producer-metrics -> metricName:io-wait-time-ns-avg -> value:3.640977828333333E8
     * groupName:producer-metrics -> metricName:buffer-exhausted-rate -> value:0.0
     * groupName:producer-metrics -> metricName:request-size-avg -> value:27.5
     * groupName:producer-metrics -> metricName:request-size-max -> value:31.0
     * groupName:producer-metrics -> metricName:incoming-byte-total -> value:383.0
     * groupName:producer-metrics -> metricName:bufferpool-wait-ratio -> value:0.0
     * groupName:producer-metrics -> metricName:waiting-threads -> value:0.0
     * groupName:producer-metrics -> metricName:requests-in-flight -> value:0.0
     * groupName:producer-metrics -> metricName:connection-close-total -> value:0.0
     * groupName:producer-metrics -> metricName:batch-size-max -> value:NaN
     * groupName:producer-metrics -> metricName:io-time-ns-avg -> value:2393190.0
     * groupName:producer-metrics -> metricName:produce-throttle-time-avg -> value:0.0
     * groupName:producer-metrics -> metricName:bufferpool-wait-time-total -> value:0.0
     * groupName:producer-metrics -> metricName:io-waittime-total -> value:2.184586697E9
     * groupName:producer-metrics -> metricName:response-rate -> value:0.05869577977343429
     * groupName:producer-metrics -> metricName:successful-authentication-rate -> value:0.0
     * groupName:producer-metrics -> metricName:record-queue-time-max -> value:NaN
     * groupName:producer-metrics -> metricName:produce-throttle-time-max -> value:0.0
     * groupName:producer-metrics -> metricName:record-retry-total -> value:0.0
     * groupName:producer-metrics -> metricName:connection-creation-rate -> value:0.029332394696
     * groupName:producer-metrics -> metricName:record-size-avg -> value:NaN
     * groupName:producer-metrics -> metricName:batch-split-rate -> value:0.0
     * groupName:producer-metrics -> metricName:record-send-rate -> value:0.0
     * groupName:producer-metrics -> metricName:reauthentication-latency-avg -> value:NaN
     * groupName:producer-metrics -> metricName:select-total -> value:6.0
     * groupName:producer-metrics -> metricName:network-io-total -> value:4.0
     * groupName:producer-metrics -> metricName:buffer-total-bytes -> value:1.048576E8
     * groupName:producer-metrics -> metricName:buffer-exhausted-total -> value:0.0
     * groupName:producer-metrics -> metricName:failed-authentication-rate -> value:0.0
     * groupName:producer-metrics -> metricName:compression-rate-avg -> value:NaN
     * groupName:producer-metrics -> metricName:batch-split-total -> value:0.0
     * groupName:producer-metrics -> metricName:select-rate -> value:0.1759014951627089
     * groupName:producer-metrics -> metricName:connection-close-rate -> value:0.0
     * groupName:producer-metrics -> metricName:successful-reauthentication-rate -> value:0.0
     * groupName:producer-metrics -> metricName:reauthentication-latency-max -> value:NaN
     * groupName:producer-metrics -> metricName:connection-creation-total -> value:1.0
     * groupName:producer-metrics -> metricName:metadata-age -> value:4.051
     * groupName:producer-metrics -> metricName:iotime-total -> value:1.435914E7
     * groupName:producer-metrics -> metricName:record-size-max -> value:NaN
     * groupName:producer-metrics -> metricName:record-error-rate -> value:0.0
     * groupName:producer-metrics -> metricName:record-send-total -> value:0.0
     * groupName:producer-metrics -> metricName:buffer-available-bytes -> value:1.048576E8
     * groupName:producer-metrics -> metricName:records-per-request-avg -> value:NaN
     * groupName:producer-metrics -> metricName:network-io-rate -> value:0.11732957878681216
     * groupName:producer-metrics -> metricName:record-error-total -> value:0.0
     * groupName:producer-metrics -> metricName:outgoing-byte-total -> value:55.0
     * groupName:producer-metrics -> metricName:batch-size-avg -> value:NaN
     * groupName:producer-metrics -> metricName:response-total -> value:2.0
     * groupName:producer-metrics -> metricName:request-total -> value:2.0
     * groupName:producer-metrics -> metricName:record-retry-rate -> value:0.0
     * groupName:producer-metrics -> metricName:successful-reauthentication-total -> value:0.0
     * groupName:producer-metrics -> metricName:io-ratio -> value:4.209656992084433E-4
     * groupName:producer-metrics -> metricName:request-latency-avg -> value:NaN
     * groupName:app-info
     * groupName:app-info -> metricName:commit-id -> value:18a913733fb71c01
     * groupName:app-info -> metricName:version -> value:2.3.1
     * groupName:app-info -> metricName:start-time-ms -> value:1585985924024
     * groupName:producer-node-metrics
     * groupName:producer-node-metrics -> metricName:incoming-byte-total -> value:383.0
     * groupName:producer-node-metrics -> metricName:request-latency-avg -> value:NaN
     * groupName:producer-node-metrics -> metricName:request-latency-max -> value:NaN
     * groupName:producer-node-metrics -> metricName:request-size-avg -> value:27.5
     * groupName:producer-node-metrics -> metricName:response-rate -> value:0.05869233478107759
     * groupName:producer-node-metrics -> metricName:request-total -> value:2.0
     * groupName:producer-node-metrics -> metricName:request-size-max -> value:31.0
     * groupName:producer-node-metrics -> metricName:outgoing-byte-rate -> value:1.6132343882908
     * groupName:producer-node-metrics -> metricName:response-total -> value:2.0
     * groupName:producer-node-metrics -> metricName:request-rate -> value:0.058663068665121866
     * groupName:producer-node-metrics -> metricName:outgoing-byte-total -> value:55.0
     * groupName:producer-node-metrics -> metricName:incoming-byte-rate -> value:11.239582110576
     */
    public Map<String, List<Metric>> metricGroupNameMap() {
        Map<MetricName, ? extends Metric> metricNameMap = this.kafkaProducer.metrics();
        Map<String, List<Metric>> metricGroupNameMap = new HashMap<>();
        metricNameMap.forEach((name, metric) -> {
            String groupName = metric.metricName().group();
            if (!metricGroupNameMap.containsKey(groupName)) {

                List<Metric> metrics = new ArrayList<>(Arrays.asList(metric));
                metricGroupNameMap.put(groupName, metrics);

            } else {
                metricGroupNameMap.get(groupName).add(metric);
            }

        });
        return metricGroupNameMap;
    }


}
