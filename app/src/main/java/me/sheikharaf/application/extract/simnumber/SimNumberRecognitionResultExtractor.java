package me.sheikharaf.application.extract.simnumber;

import android.content.Context;

import me.sheikharaf.application.R;
import me.sheikharaf.application.extract.IBaseRecognitionResultExtractor;
import me.sheikharaf.application.extract.RecognitionResultEntry;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.blinkbarcode.simnumber.SimNumberScanResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Boris on 03/02/17.
 */
public class SimNumberRecognitionResultExtractor implements IBaseRecognitionResultExtractor {


    private RecognitionResultEntry.Builder mBuilder;
    private List<RecognitionResultEntry> mExtractedData;

    public SimNumberRecognitionResultExtractor(Context context) {
        mBuilder = new RecognitionResultEntry.Builder(context);
        mExtractedData = new ArrayList<>();
    }

    @Override
    public List<RecognitionResultEntry> extractData(BaseRecognitionResult result) {

        if (result == null){
            return mExtractedData;
        }

        if (result instanceof SimNumberScanResult){
            // result is obtained by scanning of sim number
            SimNumberScanResult simNumberResult = (SimNumberScanResult) result;

            mExtractedData.add(mBuilder.build(
                    R.string.PPSimNumber,
                    simNumberResult.getSimNumber()
            ));
        }

        return mExtractedData;
    }
}