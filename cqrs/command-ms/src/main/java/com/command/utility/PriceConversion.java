package com.command.utility;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Component
public class PriceConversion {
    public static ByteBuffer toByte(BigDecimal price, Schema schema){
        LogicalTypes.Decimal decimalType = (LogicalTypes.Decimal) schema.getLogicalType();
        Conversions.DecimalConversion conversion = new Conversions.DecimalConversion();

        return conversion.toBytes(
                price,
                schema,
                decimalType
        );
    }

}
