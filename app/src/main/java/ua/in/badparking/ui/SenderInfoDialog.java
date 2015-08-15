package ua.in.badparking.ui;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Dialog;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;

import ua.in.badparking.R;
import ua.in.badparking.data.TrespassController;

/**
 * Created by Dima Kovalenko on 8/12/15.
 */
public class SenderInfoDialog extends Dialog {

    private EditText emailView;
    private EditText firstNameView;
    private EditText lastNameView;
    private EditText fatherNameView;
    private EditText phoneView;

    public SenderInfoDialog(Context context) {
        super(context);
        _init();
    }

    public SenderInfoDialog(Context context, int theme) {
        super(context, theme);
        _init();
    }

    protected SenderInfoDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        _init();
    }

    private void _init() {
        setTitle(getContext().getString(R.string.your_data));
        setContentView(R.layout.dialog_sender_info);
        emailView = (EditText)findViewById(R.id.email);
        firstNameView = (EditText)findViewById(R.id.firstName);
        lastNameView = (EditText)findViewById(R.id.lastName);
        fatherNameView = (EditText)findViewById(R.id.fatherName);
        phoneView = (EditText)findViewById(R.id.phone);
        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _saveData();
                dismiss();
            }
        });

        extractPossibleInfo();

        restoreFromPrefs();
    }

    private void restoreFromPrefs() {
        // TODO
    }

    private void _saveData() {
        String firstName = firstNameView.getText().toString();
        String lastName = lastNameView.getText().toString();
        String fatherName = fatherNameView.getText().toString();
        String phone = phoneView.getText().toString();
        String email = emailView.getText().toString();

        TrespassController.INST.getTrespass().setName(lastName + " " + firstName + " " + fatherName);
        TrespassController.INST.getTrespass().setPhone(phone);
        TrespassController.INST.getTrespass().setEmail(email);

    }

    private void extractPossibleInfo() {
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                String possibleEmail = account.name;
                emailView.setText(possibleEmail);
            }
        }
    }
}