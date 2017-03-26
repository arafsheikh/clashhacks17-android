package me.sheikharaf.application.extract.serbia;

import android.content.Context;

import me.sheikharaf.application.extract.RecognitionResultEntry;
import me.sheikharaf.application.extract.mrtd.MRTDRecognitionResultExtractor;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkid.serbia.back.SerbianIDBackSideRecognitionResult;


import java.util.List;

/**
 * Created by Boris on 26/08/16.
 */
public class SerbianIDBackRecognitionResultExtractor extends MRTDRecognitionResultExtractor {

    public SerbianIDBackRecognitionResultExtractor(Context context) {
        super(context);
    }

    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {
        if (result == null) {
            return mExtractedData;
        }

        if (result instanceof SerbianIDBackSideRecognitionResult) {
            SerbianIDBackSideRecognitionResult srbIDBackResult = (SerbianIDBackSideRecognitionResult) result;
            super.extractMRZData(srbIDBackResult);
        }

        return mExtractedData;
    }
}
