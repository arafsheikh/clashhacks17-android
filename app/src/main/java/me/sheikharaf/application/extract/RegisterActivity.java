package me.sheikharaf.application.extract;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.microblink.activity.ScanCard;
import com.microblink.recognizers.BaseRecognitionResult;
import com.microblink.recognizers.RecognitionResults;
import com.microblink.recognizers.blinkbarcode.BarcodeType;
import com.microblink.recognizers.blinkbarcode.aztec.AztecScanResult;
import com.microblink.recognizers.blinkbarcode.bardecoder.BarDecoderScanResult;
import com.microblink.recognizers.blinkbarcode.pdf417.Pdf417ScanResult;
import com.microblink.recognizers.blinkbarcode.simnumber.SimNumberScanResult;
import com.microblink.recognizers.blinkbarcode.zxing.ZXingRecognizerSettings;
import com.microblink.recognizers.blinkbarcode.zxing.ZXingScanResult;
import com.microblink.recognizers.blinkid.austria.back.AustrianIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.austria.front.AustrianIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.croatia.back.CroatianIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.croatia.front.CroatianIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.czechia.back.CzechIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.czechia.front.CzechIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.eudl.EUDLRecognitionResult;
import com.microblink.recognizers.blinkid.germany.front.GermanIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.germany.mrz.GermanIDMRZSideRecognitionResult;
import com.microblink.recognizers.blinkid.malaysia.IKadRecognitionResult;
import com.microblink.recognizers.blinkid.malaysia.MyKadRecognitionResult;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognitionResult;
import com.microblink.recognizers.blinkid.mrtd.MRTDRecognizerSettings;
import com.microblink.recognizers.blinkid.romania.front.RomanianIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.serbia.back.SerbianIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.serbia.front.SerbianIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.singapore.back.SingaporeIDBackRecognitionResult;
import com.microblink.recognizers.blinkid.singapore.front.SingaporeIDFrontRecognitionResult;
import com.microblink.recognizers.blinkid.slovakia.back.SlovakIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.slovakia.front.SlovakIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkid.slovenia.back.SlovenianIDBackSideRecognitionResult;
import com.microblink.recognizers.blinkid.slovenia.front.SlovenianIDFrontSideRecognitionResult;
import com.microblink.recognizers.blinkocr.BlinkOCRRecognitionResult;
import com.microblink.recognizers.settings.RecognitionSettings;
import com.microblink.recognizers.settings.RecognizerSettings;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import me.sheikharaf.application.R;
import me.sheikharaf.application.extract.austria.AustrianIDBackSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.austria.AustrianIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.barcode.AztecRecognitionResultExtractor;
import me.sheikharaf.application.extract.barcode.BardecoderRecognitionResultExtractor;
import me.sheikharaf.application.extract.barcode.Pdf417RecognitionResultExtractor;
import me.sheikharaf.application.extract.barcode.ZXingRecognitionResultExtractor;
import me.sheikharaf.application.extract.blinkInput.BlinkOcrRecognitionResultExtractor;
import me.sheikharaf.application.extract.croatia.CroatianIDBackSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.croatia.CroatianIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.czechia.CzechIDBackSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.czechia.CzechIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.eudl.EUDLRecognitionResultExtractor;
import me.sheikharaf.application.extract.germany.GermanIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.germany.GermanIDMRZSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.malaysia.IKadRecognitionResultExtractor;
import me.sheikharaf.application.extract.malaysia.MyKadRecognitionResultExtractor;
import me.sheikharaf.application.extract.mrtd.MRTDRecognitionResultExtractor;
import me.sheikharaf.application.extract.romania.RomanianIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.serbia.SerbianIDBackRecognitionResultExtractor;
import me.sheikharaf.application.extract.serbia.SerbianIDFrontRecognitionResultExtractor;
import me.sheikharaf.application.extract.simnumber.SimNumberRecognitionResultExtractor;
import me.sheikharaf.application.extract.singapore.SingaporeIDBackRecognitionResultExtractor;
import me.sheikharaf.application.extract.singapore.SingaporeIDFrontRecognitionResultExtractor;
import me.sheikharaf.application.extract.slovakia.SlovakIDBackSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.slovakia.SlovakIDFrontSideRecognitionResultExtractor;
import me.sheikharaf.application.extract.slovenia.SlovenianIDBackRecognitionResultExtractor;
import me.sheikharaf.application.extract.slovenia.SlovenianIDFrontRecognitionResultExtractor;

public class RegisterActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PASSPORT = 0x101;
    public static final int REQUEST_CODE_QR = 0x102;
    private String MRZ = null;
    private String UID = null;
    private Button regPass, regAadh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        regPass = (Button) findViewById(R.id.btnRegPassport);
        regAadh = (Button) findViewById(R.id.btnRegAadhar);

        regPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPassportScan();
            }
        });
        regAadh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQRScan();
            }
        });

    }

    private void startPassportScan() {
        // Intent for ScanCard Activity
        Intent intent = new Intent(this, ScanCard.class);

        // set your licence key
        // obtain your licence key at http://microblink.com/login or
        // contact us at http://help.microblink.com
        intent.putExtra(ScanCard.EXTRAS_LICENSE_KEY, "ACUXRKSI-5Z7ONXOS-ZRAV7JNV-P5MZRWZB-SCAPI5FA-P3Y5IFR5-D2OWTZW4-WSRBA5JT");

        RecognitionSettings settings = new RecognitionSettings();
        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray(setupMRTDSettingsArray());
        intent.putExtra(ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings);

        // Starting Activity
        startActivityForResult(intent, REQUEST_CODE_PASSPORT);
    }

    private void startQRScan() {
        // Intent for ScanCard Activity
        Intent intent = new Intent(this, ScanCard.class);

        // set your licence key
        // obtain your licence key at http://microblink.com/login or
        // contact us at http://help.microblink.com
        intent.putExtra(ScanCard.EXTRAS_LICENSE_KEY, "ACUXRKSI-5Z7ONXOS-ZRAV7JNV-P5MZRWZB-SCAPI5FA-P3Y5IFR5-D2OWTZW4-WSRBA5JT");

        RecognitionSettings settings = new RecognitionSettings();
        // setup array of recognition settings (described in chapter "Recognition
        // settings and results")
        settings.setRecognizerSettingsArray(setupQRSettingsArray());
        intent.putExtra(ScanCard.EXTRAS_RECOGNITION_SETTINGS, settings);

        // Starting Activity
        startActivityForResult(intent, REQUEST_CODE_QR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PASSPORT) {
            if (resultCode == ScanCard.RESULT_OK && data != null) {
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                RecognitionResults result = data.getParcelableExtra(ScanCard.EXTRAS_RECOGNITION_RESULTS);

                // get array of recognition results
                BaseRecognitionResult[] resultArray = result.getRecognitionResults();
                // Each element in resultArray inherits BaseRecognitionResult class and
                // represents the scan result of one of activated recognizers that have
                // been set up. More information about this can be found in
                // "Recognition settings and results" chapter

                BaseRecognitionResult mData = resultArray[0];
                IBaseRecognitionResultExtractor mResultExtractor;
                List<RecognitionResultEntry> extractedData;
                if (mData instanceof SingaporeIDFrontRecognitionResult) {
                    mResultExtractor = new SingaporeIDFrontRecognitionResultExtractor(this);
                } else if ( mData instanceof SingaporeIDBackRecognitionResult) {
                    mResultExtractor = new SingaporeIDBackRecognitionResultExtractor( this );
                } else if (mData instanceof AustrianIDBackSideRecognitionResult) {
                    mResultExtractor = new AustrianIDBackSideRecognitionResultExtractor(this);
                } else if (mData instanceof AustrianIDFrontSideRecognitionResult) {
                    mResultExtractor = new AustrianIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof CroatianIDBackSideRecognitionResult) {
                    mResultExtractor = new CroatianIDBackSideRecognitionResultExtractor(this);
                } else if (mData instanceof CroatianIDFrontSideRecognitionResult) {
                    mResultExtractor = new CroatianIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof CzechIDBackSideRecognitionResult) {
                    mResultExtractor = new CzechIDBackSideRecognitionResultExtractor(this);
                } else if (mData instanceof CzechIDFrontSideRecognitionResult) {
                    mResultExtractor = new CzechIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof GermanIDMRZSideRecognitionResult) {
                    mResultExtractor = new GermanIDMRZSideRecognitionResultExtractor(this);
                } else if (mData instanceof GermanIDFrontSideRecognitionResult) {
                    mResultExtractor = new GermanIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof RomanianIDFrontSideRecognitionResult) {
                    mResultExtractor = new RomanianIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof SlovakIDBackSideRecognitionResult) {
                    mResultExtractor = new SlovakIDBackSideRecognitionResultExtractor(this);
                } else if (mData instanceof SlovakIDFrontSideRecognitionResult) {
                    mResultExtractor = new SlovakIDFrontSideRecognitionResultExtractor(this);
                } else if (mData instanceof SerbianIDBackSideRecognitionResult) {
                    mResultExtractor = new SerbianIDBackRecognitionResultExtractor(this);
                } else if (mData instanceof SerbianIDFrontSideRecognitionResult) {
                    mResultExtractor = new SerbianIDFrontRecognitionResultExtractor(this);
                } else if (mData instanceof SlovenianIDBackSideRecognitionResult) {
                    mResultExtractor = new SlovenianIDBackRecognitionResultExtractor(this);
                } else if (mData instanceof SlovenianIDFrontSideRecognitionResult) {
                    mResultExtractor = new SlovenianIDFrontRecognitionResultExtractor(this);
                } else if (mData instanceof IKadRecognitionResult) {
                    mResultExtractor = new IKadRecognitionResultExtractor(this);
                } else if(mData instanceof MRTDRecognitionResult) {
                    mResultExtractor = new MRTDRecognitionResultExtractor(this);
                } else if(mData instanceof EUDLRecognitionResult) {
                    mResultExtractor = new EUDLRecognitionResultExtractor(this);
                } else if (mData instanceof Pdf417ScanResult) {
                    mResultExtractor = new Pdf417RecognitionResultExtractor(this);
                } else if (mData instanceof ZXingScanResult) {
                    mResultExtractor = new ZXingRecognitionResultExtractor(this);
                } else if (mData instanceof BarDecoderScanResult) {
                    mResultExtractor = new BardecoderRecognitionResultExtractor(this);
                } else if (mData instanceof SimNumberScanResult) {
                    mResultExtractor = new SimNumberRecognitionResultExtractor(this);
                } else if (mData instanceof AztecScanResult) {
                    mResultExtractor = new AztecRecognitionResultExtractor(this);
                } else if (mData instanceof BlinkOCRRecognitionResult) {
                    mResultExtractor = new BlinkOcrRecognitionResultExtractor(this);
                } else if (mData instanceof MyKadRecognitionResult) {
                    mResultExtractor = new MyKadRecognitionResultExtractor(this);
                } else {
                    mResultExtractor = new BaseRecognitionResultExtractor(this);
                }

                // Extract data from BaseRecognitionResult
                extractedData = mResultExtractor.extractData(mData);

                for (RecognitionResultEntry entry: extractedData) {
                    if (entry.getKey().equals("MRZ text")) {
                        saveMRZ(entry.getValue());
                    }
                }
            }
        } else if (requestCode == REQUEST_CODE_QR) {
            if (resultCode == ScanCard.RESULT_OK && data != null) {
                // perform processing of the data here

                // for example, obtain parcelable recognition result
                Bundle extras = data.getExtras();
                RecognitionResults result = data.getParcelableExtra(ScanCard.EXTRAS_RECOGNITION_RESULTS);

                // get array of recognition results
                BaseRecognitionResult[] dataArray = result.getRecognitionResults();
                for(BaseRecognitionResult baseResult : dataArray) {
                    if (baseResult instanceof ZXingScanResult) {
                        ZXingScanResult res = (ZXingScanResult) baseResult;

                        // getBarcodeType getter will return a BarcodeType enum that will define
                        // the type of the barcode scanned
                        BarcodeType barType = res.getBarcodeType();
                        // getStringData getter will return the string version of barcode contents
                        String barcodeData = res.getStringData();

                        InputStream in = new ByteArrayInputStream(barcodeData.getBytes());
                        try {
                            XmlPullParser parser = Xml.newPullParser();
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(in, null);
                            parser.nextTag();
                            parser.require(XmlPullParser.START_TAG, null, "PrintLetterBarcodeData");
                            String uid = parser.getAttributeValue(null, "uid");
                            saveAadhar(uid);
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                in.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                // Or, you can pass the intent to another activity
                //data.setComponent(new ComponentName(this, ResultActivity.class));
                //startActivity(data);
            }
        }
    }

    private RecognizerSettings[] setupMRTDSettingsArray() {
        MRTDRecognizerSettings sett = new MRTDRecognizerSettings();

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }

    private RecognizerSettings[] setupQRSettingsArray() {
        ZXingRecognizerSettings sett=  new ZXingRecognizerSettings();
        // disable scanning of white barcodes on black background
        sett.setInverseScanning(false);
        // activate scanning of QR codes
        sett.setScanQRCode(true);

        // now add sett to recognizer settings array that is used to configure
        // recognition
        return new RecognizerSettings[] { sett };
    }

    private void saveMRZ(String value) {
        MRZ = value;
        regPass.setEnabled(false);
        if (UID != null) {
            sendToServer();
        }
    }

    private void saveAadhar(String value) {
        UID = value;
        regAadh.setEnabled(false);
        if (MRZ != null) {
            sendToServer();
        }
    }

    private void sendToServer() {
        SharedPreferences.Editor editor = getSharedPreferences("pref", MODE_PRIVATE).edit();
        editor.putBoolean("registered", true);
        editor.apply();

        // send MRZ, Aadhar, Public key
        String pubKey;
        try {
            pubKey = returnBase64PublicKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        final RequestQueue queue = Volley.newRequestQueue(this);
        String endpoint = "http://139.59.5.204/ClashHacks-api/public/register_mrz";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, endpoint,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("mrz", MRZ);
                System.out.println(params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 10, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);

        finish();
    }

    public String returnBase64PublicKey() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(4096);
        KeyPair keyPair = kpg.genKeyPair();
        byte[] pri = keyPair.getPrivate().getEncoded();
        byte[] pub = keyPair.getPublic().getEncoded();
        SharedPreferences.Editor editor = getSharedPreferences("key", MODE_PRIVATE).edit();
        editor.putString("pri_key", Base64.encodeToString(pri, Base64.DEFAULT));
        editor.putString("pub_key", Base64.encodeToString(pub, Base64.DEFAULT));
        editor.apply();
        return Base64.encodeToString(pub, Base64.DEFAULT);
    }
}
