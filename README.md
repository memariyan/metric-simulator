# metric-simulator
This is a project for simulating prometheus batch metrics.
You can examine your alert rules and your queries by this simulator.
Just define your metric type, and value conditions and insert conditions to insert batch metrics

There are some request curl examples here :

# insert custom metrics

```bash
curl --location --request PUT 'http://localhost:6090/metrics' \
--header 'Content-Type: application/json' \
--data '{
    "groups": [
        {
            "name": "gateway_health_group",
            "metrics": [
                {
                    "type": 2,
                    "name": "gateway.health",
                    "tags": [
                        {
                            "name": "gatewayCode",
                            "value": "001"
                        },
                        {
                            "name": "gatewayType",
                            "value": "0"
                        }
                    ],
                    "value": 0.0,
                    "pauseDuration": 10000
                },
                {
                    "type": 2,
                    "name": "gateway.health",
                    "tags": [
                        {
                            "name": "gatewayCode",
                            "value": "002"
                        },
                        {
                            "name": "gatewayType",
                            "value": "0"
                        }
                    ],
                    "value": 0.0
                }
            ]
        }
    ]
}'
```

# generate batch metrics

```bash
curl --location 'http://localhost:6090/metrics/generate' \
--header 'Content-Type: application/json' \
--data '{
   "groupConfigs":[
      {
         "groupName":"gateway_token_group",
         "partitionConfigs":[
            {
               "metricType":3,
               "count":1000,
               "metricName":"gateway.payment.token",
               "metricValue":{
                  "type":"random",
                  "min":500,
                  "max":4000,
                  "precision":1
               },
               "metricTags":[
                  {
                     "name":"gatewayCode",
                     "value":{
                        "type":"weighted",
                        "contents":[
                           "001",
                           "002",
                           "003",
                           "004"
                        ],
                        "weights":[
                           20,
                           25,
                           35,
                           20
                        ]
                     }
                  },
                  {
                     "name":"businessId",
                     "value":{
                        "type":"constant",
                        "content":"sample-business-id"
                     }
                  },
                  {
                     "name":"gatewayType",
                     "value":{
                        "type":"constant",
                        "content":"0"
                     }
                  },
                  {
                     "name":"success",
                     "value":{
                        "type":"constant",
                        "content":"1"
                     }
                  }
               ],
               "pauseDuration":{
                  "type":"weighted-random",
                  "bounds":[
                     {
                        "min":100,
                        "max":2000
                     },
                     {
                        "min":2000,
                        "max":4000
                     }
                  ],
                  "weights":[
                     70,
                     30
                  ]
               }
            }
         ]
      }
   ]
}'
```

# generate and insert batch metrics

```bash
curl --location 'http://localhost:6090/metrics' \
--header 'Content-Type: application/json' \
--data '{
   "groupConfigs":[
      {
         "groupName":"purchase_count_group",
         "partitionConfigs":[
            {
               "metricType":0,
               "count":100,
               "metricName":"purchase",
               "metricTags":[
                  {
                     "name":"businessId",
                     "value":{
                        "type":"weighted",
                        "contents":[
                           "sample-business-id"
                        ],
                        "weights":[
                           100
                        ]
                     }
                  },
                  {
                     "name":"status",
                     "value":{
                        "type":"constant",
                        "content":"8"
                     }
                  }
               ],
               "pauseDuration":{
                  "type":"weighted-random",
                  "bounds":[
                     {
                        "min":500,
                        "max":1000
                     },
                     {
                        "min":1000,
                        "max":2000
                     },
                     {
                        "min":2000,
                        "max":4000
                     }
                  ],
                  "weights":[
                     80,
                     15,
                     5
                  ]
               }
            }
         ]
      }
   ]
}'
```