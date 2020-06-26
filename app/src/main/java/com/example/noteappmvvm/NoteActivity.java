package com.example.noteappmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.noteappmvvm.database.NoteRepository;
import com.example.noteappmvvm.model.Note;
import com.example.noteappmvvm.util.Utility;

public class NoteActivity extends AppCompatActivity implements
        View.OnTouchListener,
        GestureDetector.OnGestureListener ,
        GestureDetector.OnDoubleTapListener ,
        View.OnClickListener ,
        TextWatcher {

    private static final String TAG = "NoteActivity";
    public static final int EDIT_MODE_ENABLED = 1;
    public static final int EDIT_MODE_DISABLED = 0;

    private LinedEditText linedEditText;
    private EditText editTitle;
    private TextView viewTitle;

    private RelativeLayout checkContainer, backArrowContainer;
    private ImageButton checkButton, backArrowButton;


    private Boolean isNewNote;

    private Note initialNote;
    private Note finalNote;
    private GestureDetector gestureDetector;

    private int mode;

    private NoteRepository noteRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        linedEditText = findViewById(R.id.note_text);
        editTitle = findViewById(R.id.note_edit_title);
        viewTitle = findViewById(R.id.note_text_title);

        checkContainer= findViewById(R.id.check_container);
        backArrowContainer = findViewById(R.id.back_arrow_container);

        checkButton = findViewById(R.id.toolbar_check);
        backArrowButton = findViewById(R.id.toolbar_back_arrow);
        noteRepository = new NoteRepository(this);

        if (getIncomingIntent()) {
            /**this is a  new Note (EDIT MODE) * */
            setNewNoteProperties();
            enableEditMode();


        } else {
            /** this is NOT a new Note(VIEW MODE) **/
            setNoteProperties();
            disableContentInteraction();
        }

        setListeners();


    }

    private void saveChanges(){
        if (isNewNote){
            saveNewNote();
        }else {
            //update note
            updateNote();
        }
    }

    private void updateNote(){
        noteRepository.updateNote(finalNote);
    }

    private void saveNewNote(){
        noteRepository.insertNote(finalNote);
    }

    private void setListeners() {
        linedEditText.setOnTouchListener(this);
        gestureDetector = new GestureDetector(this,this);
        viewTitle.setOnClickListener(this);
        checkButton.setOnClickListener(this);
        backArrowButton.setOnClickListener(this);
        editTitle.addTextChangedListener(this);
    }
    private Boolean getIncomingIntent(){
        if (getIntent().hasExtra("selected_note")) {
            initialNote = getIntent().getParcelableExtra("selected_note");

            finalNote = new Note();

            finalNote.setTitle(initialNote.getTitle());
            finalNote.setContent(initialNote.getContent());
            finalNote.setTimestamp(initialNote.getTimestamp());
            finalNote.setId(initialNote.getId());

            mode = EDIT_MODE_DISABLED;
            isNewNote = false;
            return false;
        }

        mode = EDIT_MODE_ENABLED;
        isNewNote = true;
        return true;
    }

    private void hideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();

        if (view == null){
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void disableContentInteraction(){
        linedEditText.setKeyListener(null);
        linedEditText.setFocusable(false);
        linedEditText.setFocusableInTouchMode(false);
        linedEditText.setCursorVisible(false);
        linedEditText.clearFocus();
    }

    private void enableContentInteraction(){
        linedEditText.setKeyListener(new EditText(this).getKeyListener());
        linedEditText.setFocusable(true);
        linedEditText.setFocusableInTouchMode(true);
        linedEditText.setCursorVisible(true);
        linedEditText.requestFocus();
    }

    private void enableEditMode(){
        backArrowContainer.setVisibility(View.GONE);
        checkContainer.setVisibility(View.VISIBLE);

        viewTitle.setVisibility(View.GONE);
        editTitle.setVisibility(View.VISIBLE);

        enableContentInteraction();

        mode = EDIT_MODE_ENABLED;
    }

    private void disableEditMode(){
        backArrowContainer.setVisibility(View.VISIBLE);
        checkContainer.setVisibility(View.GONE);

        viewTitle.setVisibility(View.VISIBLE);
        editTitle.setVisibility(View.GONE);

        mode = EDIT_MODE_DISABLED;

        disableContentInteraction();

        String temp = linedEditText.getText().toString();
        temp = temp.replace("\n", "");
        temp = temp.replace(" ", "");

        if (temp.length() > 0){
            finalNote.setTitle(linedEditText.getText().toString());
            finalNote.setContent(linedEditText.getText().toString());

            String timestamp = Utility.getCurrentTimestamp();
            finalNote.setTimestamp(timestamp);
        }

        if (!finalNote.getContent().equals(initialNote.getContent())
                || !finalNote.getTitle().equals(initialNote.getTitle())){
            saveChanges();
        }

    }

    private void setNoteProperties() {
        viewTitle.setText(initialNote.getTitle());
        editTitle.setText(initialNote.getTitle());
        linedEditText.setText(initialNote.getContent());
    }

    private void setNewNoteProperties(){
        viewTitle.setText("New Note");
        editTitle.setText("New Note");

        initialNote = new Note();
        finalNote = new Note();

        initialNote.setTitle("Note title");
        finalNote.setTitle("Note title");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.i(TAG, "onDoubleTap: double tapped");
        enableEditMode();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.toolbar_check:
                hideSoftKeyboard();
                disableEditMode();
                break;
            case R.id.note_text_title:
                enableEditMode();
                editTitle.requestFocus();
                editTitle.setSelection(editTitle.length());
                break;
            case R.id.toolbar_back_arrow:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mode == EDIT_MODE_ENABLED){
            onClick(checkButton);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("mode", mode);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mode = savedInstanceState.getInt("mode");
        if (mode == EDIT_MODE_ENABLED){
            enableEditMode();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        viewTitle.setText(s.toString());
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}