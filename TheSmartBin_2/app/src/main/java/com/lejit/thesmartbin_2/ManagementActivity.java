package com.lejit.thesmartbin_2;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ManagementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        FloatingActionButton managementButton=(FloatingActionButton)findViewById(R.id.fab_arc_menu_2);
        managementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent management =new Intent(ManagementActivity.this,ManagementActivity.class);
                ManagementActivity.this.startActivity(management);
            }
        });
        FloatingActionButton logout=(FloatingActionButton)findViewById(R.id.fab_arc_menu_1);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent logoutCall=new Intent(ManagementActivity.this,MainActivity.class);
                ManagementActivity.this.startActivity(logoutCall);
            }
        });
        FloatingActionButton userProfile=(FloatingActionButton)findViewById(R.id.fab_arc_menu_3);
        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userpro=new Intent(ManagementActivity.this,UserProfile.class);
                ManagementActivity.this.startActivity(userpro);
            }
        });
        FloatingActionButton about=(FloatingActionButton)findViewById(R.id.fab_arc_menu_4);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent aboutPage=new Intent(ManagementActivity.this,About.class);
                ManagementActivity.this.startActivity(aboutPage);
            }
        });
    }
    public void onDestroy() {

        super.onDestroy();

    }
}
