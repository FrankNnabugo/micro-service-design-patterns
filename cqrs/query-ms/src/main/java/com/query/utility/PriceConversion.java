package com.query.utility;

import org.apache.avro.Conversions;
import org.apache.avro.LogicalTypes;
import org.apache.avro.Schema;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.nio.ByteBuffer;

@Component
public class PriceConversion {
    private static final Conversions.DecimalConversion CONVERSION =
            new Conversions.DecimalConversion();

    public static BigDecimal toBigDecimal(ByteBuffer price, Schema schema){
        LogicalTypes.Decimal decimalType = (LogicalTypes.Decimal) schema.getLogicalType();
        Conversions.DecimalConversion conversion = new Conversions.DecimalConversion();
        return CONVERSION.fromBytes(price, schema, decimalType);

    }
}
