# CardioAI
The CardioAI repository contains a series of utilities for extracting, converting, and transmitting electrocardiogram data files originating from various databases hosted at [PhysioNet](https://physionet.org/). The utilities were developed by Group 2 (Cohord 20024) as part of the Professional Certificate Program in Product Management from the Massachusetts Institute of Technology (MIT).

PhysioNet stores the electrocardiogram data files on various WaveForm Database formats, which are also referred to as WFDB. A WFDB record is comprised of a header file and any number of signal and annotation files in binary format. Because the CardioAI ingestion layer expects data files to be transmitted in JavaScript Object Notation (JSON) format over HTTP, the team developed these utilities to convert the extracted electrocardiogram data into suitable payloads and messages to be consumed by the ingestion layer.

The team is using the WFDB applications from the WFDB Software Package to work with the WFDB files. More specifically, the utilities use the `rdsamp` command to extract the electrocardiogram data using various criteria. The utilities are not exemplifications of production-level code. They have been written to achieve the intended functionality using multiple approaches in the fastest way possible and do not claim to be either logically or functionally correct. Lastly, the decision to concentrate on Solution Architecture instead of software development as part of the Impact Project has allocated more attention to other areas of concentration.

## Step 1: Create a Manifest
The team concluded that it would be more flexible to use manifest files instead of pointing the DataExtractor utility at an entire dataset. Additionally, working with manifest files provides extra flexibility, such as extracting data from multiple datasets, and they could be saved as configurations. The following command will create a manifest file for the [PTB-XL](https://physionet.org/content/ptb-xl/1.0.3/) dataset:

`find ./ptb-xl/ -type f -regex '.*\.hea$' > ptb-xl.mf`

## Step 2: Data Extraction
The DataExtractor is a Java utility that generates a shell script to extract electrocardiogram data. Consequently, pulling the data is a two-step process. PhysioNet offers Java bindings to facilitate interoperability with the WFDB library via WFDB-SWIG. However, since we are only interested in data extraction at this time, we used a more direct approach. The supported command line arguments are summarized below.

| Short Form  | Long Form     | Function                                      | Required |
| :---        | :---          | :---                                          | :---:    | 
| -m          | --manifest    | The manifest file that is to be processed.    | true     |
| -f          | --from-time   | Begin at the specified time (sec).            | false    |
| -t          | --to-time     | Stop at the specified time (sec).             | false    |
| -d          | --destination | The destination folder of the extracted data. | true     |
| -s          | --script      | The name of the data extraction script.       | true     |

Create the shell script by running the DataExtractor Java utility as follows:

`java -jar data-extractor-1.0.0.jar -m ptb-xl.mf -f 0 -t 60 -d ./data/ -s ptb-xl.sh`

Make the scripts executable after creation as follows:

`chmod +x ptb-xl.sh`

Lastly, execute the script to extract the electrocardiogram data into the destination directory as follows:

`./ptb-xl.sh`

The Java utility names the data files using a Universally Unique Identifier (UUID) to avoid potential collisions when extracting data from various datasets contained in a single or across multiple manifest files. Verify the data extraction is progressing as expected by inspecting one or more of the data files as follows:

`cat ./data/b637c14f-65fe-4aaf-8fca-5cbb30fd7e32.txt`

```
'sample #','I','II','III','AVR','AVL','AVF','V1','V2','V3','V4','V5','V6'
0,-12,-140,-128,77,58,-133,100,69,55,70,-30,-75
1,4,-138,-142,67,73,-139,98,71,55,70,-29,-74
2,7,-132,-139,63,73,-135,92,81,55,70,-25,-70
3,7,-129,-136,61,72,-132,89,76,56,70,-22,-67
4,10,-123,-133,57,72,-128,84,71,61,70,-17,-62
5,10,-120,-130,55,70,-125,80,66,60,70,-15,-59
6,9,-120,-128,56,69,-124,85,60,60,65,-16,-56
7,5,-120,-125,58,65,-122,85,55,57,65,-14,-55
8,5,-119,-124,58,64,-121,86,51,55,64,-19,-55
9,6,-120,-126,58,66,-123,91,49,55,60,-20,-55
10,3,-119,-122,59,63,-120,89,45,55,60,-12,-55
11,12,-120,-133,54,73,-126,93,43,55,62,-6,-53
...
```
**Example 1:** A record generated from the PTB-XL ECG dataset (12-lead)

## Step 3: Data Conversion

The PayloadGenerator is a Java utility that reads the extracted data from the previous step and generates JSON serialized payloads for the CardioAI ingestion layer. The payloads will be part of a final message containing additional fields. The supported command line arguments are summarized below.

| Short Form  | Long Form     | Function                                      | Required |
| :---        | :---          | :---                                          | :---:    | 
| -s          | --source      | The source folder of the extracted data.      | true     |
| -d          | --destination | The destination folder of the payload files.  | true     |

Create the payload files by running the PayloadGenerator Java utility as follows:

`java -jar payload-generator-1.0.0.jar -s ./data/ -d ./payloads/`

The data model has a `sequence` and an `index` field to facilitate segmenting extensive electrocardiogram readings into multiple payloads for more efficient transmission using various messages. The former field indicates the number of payloads in the sequence, and the latter the position of each payload in the sequence.

```
{
  "deviceId": "351d51f7-00ee-4df2-971d-769c6607d2a8",
  "customerId": "2516fa1f-3231-4eb1-bab7-64f949ac7b24",
  "sequenceId": "f8d9e673-f562-4b79-a32f-699ac54517e1",
  "sequence": 1,
  "index": 1,
  "headers": "INDEX,I,II,III,AVR,AVL,AVF,V1,V2,V3,V4,V5,V6",
  "data": [
    "0,-70,-36,34,52,-51,-1,-6,-55,-80,-110,-116,-75",
    "1,-81,-35,46,57,-63,6,-8,-55,-79,-109,-114,-73",
    "2,-95,-31,64,64,-80,16,-4,-55,-74,-112,-114,-70",
    "3,-86,-30,56,58,-71,13,-6,-55,-68,-104,-107,-63",
    "4,-89,-24,65,56,-76,20,-5,-56,-68,-107,-107,-61",
    "5,-80,-29,52,55,-66,11,-5,-54,-57,-104,-105,-57",
    "6,-85,11,96,37,-91,53,-6,-61,-68,-83,-73,-30",
    "7,-69,40,108,14,-89,74,-14,-43,-64,-38,26,43",
    "8,-13,54,67,-21,-40,60,-3,51,48,163,364,217",
    "9,96,-56,-152,-20,124,-104,69,226,199,260,421,222",
    "10,331,-506,-838,88,585,-672,-163,-102,-162,-231,-143,-135",
    "11,424,-1028,-1452,302,938,-1240,-569,-874,-954,-1074,-870,-656"
    ...
  ],
  "recordId": "11e6ab36-99cd-44e6-ac02-973b0254ebf4",
  "created": 1751263775013
}
```
**Example 2:** A payload generated from the PTB-XL ECG dataset (12-lead)

```
{
  "deviceId": "fbe013a2-0f2f-43f1-9a76-5384a9703396",
  "customerId": "4b350011-8bb5-4fc3-8065-84e1fdcdf7ed",
  "sequenceId": "d4aa035d-9a3a-4e2e-9d2c-ae6b481bb68c",
  "sequence": 2,
  "index": 1,
  "headers": "INDEX,I,II,III,AVR,AVL,AVF,V1,V2,V3,V4,V5,V6",
  "data": [
    "0,-110,44,155,32,-133,99,5,-44,373,-94,-115,96",
    "1,-156,-4,151,80,-153,73,5,-33,373,-82,-110,104",
    "2,-145,-1,144,73,-144,71,-4,-18,376,-69,-97,111",
    ...
  ],
  "recordId": "0fca8976-6e5e-42e8-9a4d-c4dcbaecae92",
  "created": 1751263775011
}
```
**Example 3:** The first payload of a sequence of two messages.

```
{
  "deviceId": "fbe013a2-0f2f-43f1-9a76-5384a9703396",
  "customerId": "4b350011-8bb5-4fc3-8065-84e1fdcdf7ed",
  "sequenceId": "d4aa035d-9a3a-4e2e-9d2c-ae6b481bb68c",
  "sequence": 2,
  "index": 2,
  "headers": "INDEX,I,II,III,AVR,AVL,AVF,V1,V2,V3,V4,V5,V6",
  "data": [
    ...
    "52998,-95,-23,72,59,-83,24,-4,4,-96,-231,-235,-116",
    "52999,-65,6,70,29,-68,38,-5,0,-100,-237,-235,-114",
    "53000,-60,2,61,29,-60,31,-5,-7,-100,-243,-233,-110",
  ],
  "recordId": "0fca8976-6e5e-42e8-9a4d-c4dcbaecae92",
  "created": 1751263775011
}
```
**Example 4:** The second payload of a sequence of two messages.

## Step 4: Traffic Simulation

The DataExtractor is a Java utility that simulates the traffic of electrocardiogram data arriving at the CardioAI ingestion layer. The initial implementation will have only two adapter types. The primary adapter will relay data over HTTP to the service endpoints, and the secondary adapter will simulate traffic arriving directly into the Enterprise Service Bus (ESB). The latter adapter will be helpful to simulate traffic for customers integrating via AWS PrivateLink. The supported command line arguments are summarized below.

| Short Form  | Long Form     | Function                                          | Required |
| :---        | :---          | :---                                              | :---:    |
| -s          | --source      | The source folder of the generated payload files. | true     |
| -a          | --adapter     | Data relay adapter type (e.g., HTTP, ESB).        | true     |
| -n          | --name        | The name of the resource receiving traffic.       | true     |
| -o          | --origin-code | The origin code (e.g., clinical, consumer).       | true     |
| -v          | --device-code | The origin device code (e.g., D3F153).            | true     |
| -b          | --before      | Pause before initiating processes (ms).           | false    |
| -f          | --after       | Pause after each message (ms).                    | false    |
| -c          | --cycle       | Pause after each monitoring cycle (ms).           | false    |

Start the traffic simulation by running the PayloadGenerator Java utility as follows:

`java -jar traffic-simulator-1.0.0.jar -s ./payloads/ -a HTTP -n http://gateway.cardioai.cloud/data/01/ -o clinical -v D3F153 -b 3500 -f 0 -c 15000`

The utility will simulate a different origin per the available number of processor cores. An origin could be either a physical electrocardiogram device or a dedicated CardioAI edge server within a clinical facility responsible for relaying data from a group of devices. The device code in the message indicates the device that captured the electrocardiogram and is not associated with the origin.

![Diagram 1](https://github.com/AGSArchitect/CardioAI/blob/main/TrafficSimulator/diagrams/uml-traffic-simulator.png "Diagram 1")
**Diagram 1:** UML diagram depicting the main components of the Traffic Simulation utility.

## Step 5: Verification

*Under development...*

Ariel Gonzalez
