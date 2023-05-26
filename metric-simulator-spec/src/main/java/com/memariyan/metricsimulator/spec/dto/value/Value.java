package com.memariyan.metricsimulator.spec.dto.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = RandomValue.class)
@JsonSubTypes({
		@JsonSubTypes.Type(name = "constant", value = ConstantValue.class),
		@JsonSubTypes.Type(name = "random", value = RandomValue.class),
		@JsonSubTypes.Type(name = "weighted", value = WeightedValue.class),
		@JsonSubTypes.Type(name = "weighted-random", value = WeightedRandomValue.class)
})
public abstract class Value<T> {

}
