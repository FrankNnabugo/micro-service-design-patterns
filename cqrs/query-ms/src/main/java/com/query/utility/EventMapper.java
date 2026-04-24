package com.query.utility;


import com.core.ProductCreatedEvent;
import com.core.ProductUpdatedEvent;
import com.core.avro.ProductCreatedEventAvro;
import com.core.avro.ProductUpdatedEventAvro;
import org.apache.avro.Schema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Mapper(componentModel = "spring", uses = PriceConversion.class)
public interface EventMapper {
    @Mapping(target = "price",
            expression = "java(PriceConversion.toBigDecimal(avro.getPrice(), ProductCreatedEventAvro.getClassSchema().getField(\"price\").schema()))")
    ProductCreatedEvent toCreatedEvent(ProductCreatedEventAvro avro);

    @Mapping(target = "price",
            expression ="java(PriceConversion.toBigDecimal(avro.getPrice(), ProductUpdatedEventAvro.getClassSchema().getField(\"price\").schema()))" )
    ProductUpdatedEvent toUpdatedEvent(ProductUpdatedEventAvro avro);

    default BigDecimal mapPrice(ByteBuffer price, Schema schema) {
        return PriceConversion.toBigDecimal(price, schema);
    }

    default String map(CharSequence value) {
        return value == null ? null : value.toString();
    }
}
