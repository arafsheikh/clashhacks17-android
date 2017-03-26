package me.sheikharaf.application.extract.serbia;

import android.content.Context;

import me.sheikharaf.application.R;
import me.sheikharaf.application.extract.RecognitionResultEntry;
import me.sheikharaf.application.extract.blinkInput.BlinkOcrRecognitionResultExtractor;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkid.serbia.front.SerbianIDFrontSideRecognitionResult;


import java.util.List;

public class SerbianIDFrontRecognitionResultExtractor extends BlinkOcrRecognitionResultExtractor {
    public SerbianIDFrontRecognitionResultExtractor(Context context) {
        super(context);
    }

    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {

        if (result == null) {
            return mExtractedData;
        }

        if (result instanceof SerbianIDFrontSideRecognitionResult) {
            SerbianIDFrontSideRecognitionResult srbIdFrontResult = (SerbianIDFrontSideRecognitionResult) result;

            mExtractedData.add(mBuilder.build(
                    R.string.PPIssueDate,
                    srbIdFrontResult.getIssuingDate()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDateOfExpiry,
                    srbIdFrontResult.getValidUntil()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDocumentNumber,
                    srbIdFrontResult.getDocumentNumber()
            ));
        }

        return mExtractedData;
    }
}
