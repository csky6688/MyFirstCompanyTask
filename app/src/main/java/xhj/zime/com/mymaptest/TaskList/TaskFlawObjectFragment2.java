package xhj.zime.com.mymaptest.TaskList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xhj.zime.com.mymaptest.R;

import static android.app.Activity.RESULT_OK;

public class TaskFlawObjectFragment2 extends Fragment implements BaseSpinnerAdapter.OnItemClickListener, View.OnClickListener {
    SpinnerChooseAdapter adapter1, adapter2;
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    TextView mTextView1, mTextView2;
    SpinnerUtils mSpinnerUtils1, mSpinnerUtils2;

    ImageView photo1, photo2, photo3;
    private static final int TAKE_PHOTO = 1;//拍照操作

    //拍照所得到的图像的保存路径
    private Uri imageUri;

    //当前用户拍照或者从相册选择的照片的文件名
    private String fileName;

    private int mCurrentImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flaw_object2, container, false);
        initData();
        mTextView1 = (TextView) view.findViewById(R.id.flaw_level);
        mTextView2 = (TextView) view.findViewById(R.id.flaw_leixing);
        photo1 = (ImageView) view.findViewById(R.id.photo1);
        photo2 = (ImageView) view.findViewById(R.id.photo2);
        photo3 = (ImageView) view.findViewById(R.id.photo3);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter1 = new SpinnerChooseAdapter(getContext(), list1, this);
        mSpinnerUtils1 = new SpinnerUtils(getContext(), mTextView1, adapter1);
        mSpinnerUtils1.init();
        adapter2 = new SpinnerChooseAdapter(getContext(), list2, this);
        mSpinnerUtils2 = new SpinnerUtils(getContext(), mTextView2, adapter2);
        mSpinnerUtils2.init();
        photo1.setOnClickListener(this);
        photo2.setOnClickListener(this);
        photo3.setOnClickListener(this);
    }

    private void initData() {
        list1.add("一般");
        list1.add("紧急");
        list1.add("特急");
        list1.add("严重");
        list2.add("隧道渗水问题模板");
        list2.add("隧道通风问题模板");
        list2.add("隧道照明问题模板");
    }

    @Override
    public void onItemClick(View view, int position) {
        if (list1.contains(list1.get(position))) {
            Toast.makeText(getActivity(), "点击" + list1.get(position), Toast.LENGTH_SHORT).show();
            mTextView1.setText(list1.get(position));
        } else if (list2.contains(list2.get(position))) {
            Toast.makeText(getActivity(), "点击" + list2.get(position), Toast.LENGTH_SHORT).show();
            mTextView2.setText(list2.get(position));
        }

        if (mSpinnerUtils1 != null) {
            mSpinnerUtils1.closeSpinner();
        }
        if (mSpinnerUtils2 != null) {
            mSpinnerUtils2.closeSpinner();
        }

    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public void takePohto() {
        //  用时间戳的方式来命名图片文件，这样可以避免文件名称重复
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        fileName = "easyasset" + format.format(date);
        // 创建一个File对象，用于存放拍照所得到的照片
        File outputImage = new File(getContext().getExternalCacheDir(), fileName + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将File对象转换为Uri对象，以便拍照后保存
        imageUri = FileProvider.getUriForFile(getActivity(),
                "xhj.zime.com.mymaptest.fileprovider", outputImage);
        //启动系统的照相Intent
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE"); //Android系统自带的照相intent
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
        startActivityForResult(intent, TAKE_PHOTO); //以forResult模式启动照相intent
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContext().
                                getContentResolver().openInputStream(imageUri));
                        if (mCurrentImg == 1) {
                            Glide.with(getActivity()).load(bitmap).into(photo1);
                        } else if (mCurrentImg == 2) {
                            Glide.with(getActivity()).load(bitmap).into(photo2);
                        } else if (mCurrentImg == 3) {
                            Glide.with(getActivity()).load(bitmap).into(photo3);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.photo1:
                takePohto();
                mCurrentImg = 1;
                break;
            case R.id.photo2:
                takePohto();
                mCurrentImg = 2;
                break;
            case R.id.photo3:
                takePohto();
                mCurrentImg = 3;
                break;
        }
    }
}
