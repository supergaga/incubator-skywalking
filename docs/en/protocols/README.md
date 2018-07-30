# Protocols
There are two types of protocols list here. 

- [**Probe Protocol**](#probe-protocols). Include the descriptions and definitions about how agent send collected metric data and traces, also the formats of each entities.

- [**Query Protocol**](#query-protocol). The backend provide query capability to SkyWalking own UI and others. These queries are based on GraphQL.

## Probe Protocols
They also related to the probe group, for understand that, look [Concepts and Designs](../concepts-and-designs/README.md) document.
These groups are **Language based native agent protocol**, **Service Mesh protocol** and **3rd-party instrument protocol**.  


### Language based native agent protocol
This protocol is combined from two parts:
* [Cross Process Propagation Headers Protocol](Skywalking-Cross-Process-Propagation-Headers-Protocol-v1.md) is for in-wire propagation.
which is usually used to carrier the necessary info to build trace.
* [SkyWalking Trace Data Protocol](Trace-Data-Protocol.md) define the communication way and format between agent and backend.


### Service Mesh probe protocol
The probe in sidecar or proxy could use this protocol to send data to backendEnd. This service provided by gRPC, requires 
the following key info:

1. Service Name or ID at both sides.
1. Service Instance Name or ID at both sides.
1. Endpoint. URI in HTTP, service method full signature in gRPC.
1. Latency. In milliseconds.
1. Response code in HTTP
1. Status. Success or fail.
1. Protocol. HTTP, gRPC
1. DetectPoint. In Service Mesh sidecar, `client` or `server`. In normal L7 proxy, value is `proxy`.


### 3rd-party instrument protocol
3rd-party instrument protocols are not defined by SkyWalking. They are just protocols/formats, which SkyWalking is compatible and
could receive from their existed libraries. SkyWalking starts with supporting Zipkin v1, v2 data formats.

Backend is based on modulization principle, so very easy to extend a new receiver to support new protocol/format.

## Query Protocol
Query protocol follows GraphQL grammar, provides data query capabilities, which depends on your analysis metrics.

There are 5 dimensionality data is provided.
1. Metadata. Metadata includes the brief info of the whole under monitoring services and their instances, endpoints, etc.
Use multiple ways to query this meta data.
1. Metric. Metric query targets all the objects defined in [OAL script](../concepts-and-designs/oal.md). You could get the 
metric data in linear or thermodynamic matrix formats based on the aggregation functions in script. 
1. Aggregation. Aggregation query means the metric data need a secondary aggregation in query stage, which makes the query 
interfaces have some different arguments. Such as, `TopN` list of services is a very typical aggregation query, 
metric stream aggregation just calculates the metric values of each service, but the expected list needs ordering metric data
by the values.
1. Trace. Query distributed traces by this.
1. Alarm. Through alarm query, you can have alarm trend and details.

The actual query GraphQL scrips could be found in [here](../../../apm-protocol/apm-ui-protocol/src/main/resources/ui-graphql-v6).  