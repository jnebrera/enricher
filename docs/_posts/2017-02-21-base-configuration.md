---
layout: page
title: "Base Configuration"
category: conf
date: 2017-02-21 10:12:22
order: 1
---

The configuration file is a JSON format file where you specify the general properties to configure the enricher instance. This file is different from stream config file that define the KS topology.

Example configuration file:

```json
{
  "application.id": "ks-enricher-app-id",
  "bootstrap.servers": "localhost:9092",
  "num.stream.threads": 1,
  "bootstrapper.classname": "io.wizzie.ks.enricher.builder.bootstrap.KafkaBootstrapper",
  "metric.enable": true,
  "metric.listeners": ["io.wizzie.ks.enricher.metrics.ConsoleMetricListener"],
  "metric.interval": 60000,
  "multi.id": false
}
```

| Property     | Description     |  Default Value|
| :------------- | :-------------  |   :-------------:   |
| `application.id`      | This id is used to identify a group of enricher instances. Normally this id is used to identify different clients.      |  - |
| `bootstrap.servers`      | A list of host/port pairs for establishing the initial connection to the Kafka cluster. The client will make use of all servers irrespective of which servers are specified here for bootstrapping—this list only impacts the initial hosts used to discover the full set of servers. This list should be in the form `host1:port1,host2:port2`      | - |
| `num.stream.threads`      | The number of threads to execute stream processing.      | 1 |
| `bootstrapper.classname`      | The bootstrapper class reference. More info: [Bootstrapper](http://wizzie.io/enricher/bootstrapper/definition-bootstrapper.html)       | - |
| `metric.enable`      | Enable metrics system.      | false |
| `metric.listeners`      | Array with metrics listeners. More info: [Metrics](http://wizzie.io/enricher/metrics/listeners.html)      | ["io.wizzie.ks.enricher.metrics.ConsoleMetricListener"] |
| `metric.interval`      | Metric report interval (milliseconds)      |  60000 |
| `multi.id`      | This property is used when you have multiple enricher instances with different `application.id` and the enricher uses the same topic names. More Info [Multi Tenant](http://wizzie.io/enricher/conf/multi-tenant.html)      |  false |
| `metric.enable`      | Enable metrics system.      | false |

**Note:** If you want to configure specific [Kafka Streams properties](http://kafka.apache.org/documentation#streamsconfigs), you can add these properties to this config file. The properties `key.serde` and `value.serde` will be overwritten by enricher.
