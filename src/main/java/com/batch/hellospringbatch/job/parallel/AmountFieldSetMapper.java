package com.batch.hellospringbatch.job.parallel;

import com.batch.hellospringbatch.dto.AmountDto;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class AmountFieldSetMapper implements FieldSetMapper {

    @Override
    public Object mapFieldSet(FieldSet fieldSet) throws BindException {
        AmountDto amount = new AmountDto();
        amount.setIndex(fieldSet.readInt(0));
        amount.setName(fieldSet.readString(1));
        amount.setAmount(fieldSet.readInt(2));

        return amount;
    }
}
