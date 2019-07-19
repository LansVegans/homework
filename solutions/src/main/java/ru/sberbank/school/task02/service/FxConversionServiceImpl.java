package ru.sberbank.school.task02.service;

import lombok.NonNull;
import ru.sberbank.school.task02.ExternalQuotesService;
import ru.sberbank.school.task02.FxConversionService;
import ru.sberbank.school.task02.exception.FxConversionException;
import ru.sberbank.school.task02.util.ClientOperation;
import ru.sberbank.school.task02.util.Quote;
import ru.sberbank.school.task02.util.Symbol;
import ru.sberbank.school.task02.util.Volume;


import java.math.BigDecimal;
import java.util.List;

public class FxConversionServiceImpl implements FxConversionService {

    protected ExternalQuotesService externalQuotesService;

    FxConversionServiceImpl(ExternalQuotesService externalQuotesService) {
        this.externalQuotesService = externalQuotesService;
    }

    protected List<Quote> list;

    @Override
    public BigDecimal convert(@NonNull ClientOperation operation,
                              @NonNull Symbol symbol,
                              @NonNull BigDecimal amount) {

        if (amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException();
        }

        this.list = externalQuotesService.getQuotes(symbol);

        if (list == null || list.isEmpty()){
            throw new FxConversionException("The quotes are not found!");
        }

        BigDecimal upperVolume = getUpperVolume(amount);

        for (Quote quote : list) {

            if (quote.getVolumeSize().equals(upperVolume)) {
                if (operation.equals(ClientOperation.SELL)) {
                    return quote.getBid();
                } else {
                    return quote.getOffer();
                }
            }
        }

        return BigDecimal.ZERO;
    }

    protected BigDecimal getUpperVolume(BigDecimal amount) {

        BigDecimal upperVolume = BigDecimal.valueOf(Long.MAX_VALUE);
        int traceVolume = 0;

        for (Quote quote : list) {
            if (quote.getVolumeSize().compareTo(amount) > 0) {
                upperVolume = quote.getVolumeSize().min(upperVolume);
            } else {
                traceVolume++;
            }
        }

        if (traceVolume == list.size()) {
            upperVolume = Volume.INFINITY.getVolume();
        }

        return upperVolume;
    }


}
