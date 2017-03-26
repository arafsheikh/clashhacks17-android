package me.sheikharaf.application.extract.slovenia;

import android.content.Context;

import me.sheikharaf.application.R;
import me.sheikharaf.application.extract.RecognitionResultEntry;
import me.sheikharaf.application.extract.blinkInput.BlinkOcrRecognitionResultExtractor;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkid.slovenia.front.SlovenianIDFrontSideRecognitionResult;

import java.util.List;

public class SlovenianIDFrontRecognitionResultExtractor extends BlinkOcrRecognitionResultExtractor {
    public SlovenianIDFrontRecognitionResultExtractor(Context context) {
        super(context);
    }

    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {

        if (result == null) {
            return mExtractedData;
        }

        if (result instanceof SlovenianIDFrontSideRecognitionResult) {
            SlovenianIDFrontSideRecognitionResult sloIdFrontResult = (SlovenianIDFrontSideRecognitionResult) result;

            mExtractedData.add(mBuilder.build(
                    R.string.PPLastName,
                    sloIdFrontResult.getLastName()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPFirstName,
                    sloIdFrontResult.getFirstName()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPNationality,
                    sloIdFrontResult.getNationality()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPSex,
                    sloIdFrontResult.getSex()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDateOfBirth,
                    sloIdFrontResult.getDateOfBirth()
            ));

            mExtractedData.add(mBuilder.build(
                    R.string.PPDateOfExpiry,
                    sloIdFrontResult.getDateOfExpiry()
            ));

        }

        return mExtractedData;
    }
}
