package me.sheikharaf.application.extract.austria;

import android.content.Context;

import me.sheikharaf.application.R;
import me.sheikharaf.application.extract.blinkInput.BlinkOcrRecognitionResultExtractor;
import me.sheikharaf.application.extract.RecognitionResultEntry;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkid.austria.front.AustrianIDFrontSideRecognitionResult;

import java.util.List;

public class AustrianIDFrontSideRecognitionResultExtractor extends BlinkOcrRecognitionResultExtractor {
    public AustrianIDFrontSideRecognitionResultExtractor(Context context) {
        super(context);
    }

    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {

        if (result == null) {
            return mExtractedData;
        }

        if (result instanceof AustrianIDFrontSideRecognitionResult) {
            AustrianIDFrontSideRecognitionResult ausIdFrontResult = (AustrianIDFrontSideRecognitionResult) result;

            mExtractedData.add(mBuilder.build(
                    R.string.PPLastName,
                    ausIdFrontResult.getLastName()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPFirstName,
                    ausIdFrontResult.getFirstName()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDocumentNumber,
                    ausIdFrontResult.getIdentityCardNumber()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPSex,
                    ausIdFrontResult.getSex()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDateOfBirth,
                    ausIdFrontResult.getDateOfBirth()
            ));
        }

        return mExtractedData;
    }
}
