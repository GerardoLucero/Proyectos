package com.journaldev.galleryview;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class gall extends Activity  {

    ImageView selectedImage;
    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private Button boton;
    List<Bitmap> Archivos = new ArrayList<Bitmap>();
    Gallery g;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        boton = (Button) findViewById(R.id.btnTomaFoto);
        //Si no existe crea la carpeta donde se guardaran las fotos
        file.mkdirs();



        //accion para el boton
        boton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String file = ruta_fotos + getCode() + ".jpg";
                File mi_foto     = new File( file );
                try {
                    mi_foto.createNewFile();
                } catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }
                //
                Uri uri = Uri.fromFile( mi_foto );
                //Abre la camara para tomar la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Guarda imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                //Retorna a la actividad
                startActivityForResult(cameraIntent, 0);
            }

        });
        g = (Gallery) findViewById(R.id.gallery);
        selectedImage=(ImageView)findViewById(R.id.imageView);
        final ImageAdapter ia = new ImageAdapter(this, ReadSDCard());
        g.setAdapter(ia);


        g.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id) {
            }
        });

        g.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                // show the selected Image
                selectedImage.setImageBitmap(Archivos.get(position));
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        //actualizar(g);
    }

    public void actualizar(Gallery g){
        final ImageAdapter ia = new ImageAdapter(this, ReadSDCard());
        g.setAdapter(ia);
    }


    private List<String> ReadSDCard()
    {
        List<String> tFileList = new ArrayList<String>();

        //It have to be matched with the directory in SDCard
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/");

        File[] files=f.listFiles();

        for(int i=0; i<files.length; i++)
        {
            File file = files[i];

        /*It's assumed that all file in the path are in supported type*/
            tFileList.add(file.getPath());

            File imgf = new File(file.getPath());
            Archivos.add(BitmapFactory.decodeFile(imgf.getAbsolutePath()));
        }
        return tFileList;
    }



    public class ImageAdapter extends BaseAdapter {
        int mGalleryItemBackground;
        private Context mContext;
        public List<String> FileList;

        public ImageAdapter(Context c, List<String> fList) {
            mContext = c;
            FileList = fList;
            TypedArray a = obtainStyledAttributes(R.styleable.gall);
            mGalleryItemBackground = a.getResourceId(
                    R.styleable.gall_android_galleryItemBackground,0);
            a.recycle();
        }


        public int getCount() {
            return FileList.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            ImageView i = new ImageView(mContext);

            Bitmap bm = BitmapFactory.decodeFile(
                    FileList.get(position).toString());
            i.setImageBitmap(bm);

            i.setLayoutParams(new Gallery.LayoutParams(200, 200));
            i.setScaleType(ImageView.ScaleType.FIT_XY);
            i.setBackgroundResource(mGalleryItemBackground);

            return i;
        }

    }

    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "Foto" + date;
        return photoCode;
    }

}