package com.command.utility;

import com.core.ProductCreatedEvent;
import com.core.ProductDeletedEvent;
import com.core.ProductUpdatedEvent;
import com.core.avro.ProductCreatedEventAvro;
import com.core.avro.ProductDeletedEventAvro;
import com.core.avro.ProductUpdatedEventAvro;
import org.apache.avro.Schema;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Mapper(componentModel = "spring", uses = PriceConversion.class)
public interface AvroMapper {
    @Mapping(target = "price",
            expression = "java(PriceConversion.toByte(event.getPrice(), ProductCreatedEventAvro.getClassSchema().getField(\"price\").schema()))")
    ProductCreatedEventAvro toCreatedAvro(ProductCreatedEvent event);

    @Mapping(target = "price",
            expression = "java(PriceConversion.toByte(event.getPrice(), ProductUpdatedEventAvro.getClassSchema().getField(\"price\").schema()))")
    ProductUpdatedEventAvro toUpdatedAvro(ProductUpdatedEvent event);

    ProductDeletedEventAvro toDeletedAvro(ProductDeletedEvent event);

    default ByteBuffer mapPrice(BigDecimal price, Schema schema) {
        return PriceConversion.toByte(price, schema);
    }
}
