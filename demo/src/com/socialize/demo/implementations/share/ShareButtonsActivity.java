/*
 * Copyright (c) 2011 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize.demo.implementations.share;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.socialize.ShareUtils;
import com.socialize.api.action.share.SocialNetworkDialogListener;
import com.socialize.demo.DemoActivity;
import com.socialize.demo.DemoUtils;
import com.socialize.demo.R;
import com.socialize.networks.SocialNetwork;
import com.socialize.ui.auth.ShareDialogFlowController;


/**
 * @author Jason Polites
 *
 */
public class ShareButtonsActivity extends DemoActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.share_buttons_activity);
		
		Button btnShare = (Button) findViewById(R.id.btnShare);
		Button btnShareWithComment = (Button) findViewById(R.id.btnShareWithComment);
		Button btnShareSocialNetwork = (Button) findViewById(R.id.btnShareSocialNetwork);
		Button btnShareFacebook = (Button) findViewById(R.id.btnShareFacebook);
		Button btnShareTwitter = (Button) findViewById(R.id.btnShareTwitter);
		
		
		btnShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtils.showShareDialog(ShareButtonsActivity.this, entity, new SocialNetworkDialogListener() {
					@Override
					public void onAfterPost(Activity parent, SocialNetwork socialNetwork) {
						DemoUtils.showToast(parent, "Shared to " + socialNetwork.name());
					}
				});
			}
		});
		
		btnShareSocialNetwork.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtils.showShareDialog(ShareButtonsActivity.this, entity, new SocialNetworkDialogListener() {
					@Override
					public void onAfterPost(Activity parent, SocialNetwork socialNetwork) {
						DemoUtils.showToast(parent, "Shared to " + socialNetwork.name());
					}
				}, ShareUtils.SOCIAL);
			}
		});
		
		btnShareWithComment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShareUtils.showShareDialog(ShareButtonsActivity.this, entity, new SocialNetworkDialogListener() {
					
					@Override
					public boolean onContinue(Dialog dialog, SocialNetwork... networks) {
						return true;
					}
					
					@Override
					public void onFlowInterrupted(ShareDialogFlowController controller) {
						showSuccessDialog(ShareButtonsActivity.this, controller);
					}

					@Override
					public void onAfterPost(Activity parent, SocialNetwork socialNetwork) {
						DemoUtils.showToast(parent, "Shared to " + socialNetwork.name());
					}
				});
			}
		});		
	}

	
	public void showSuccessDialog(Context context, final ShareDialogFlowController controller) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Click to share");
		builder.setMessage("Click to share");
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				controller.onContinue("testing");
			}
		});
		builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				controller.onCancel();
			}
		});
		builder.create().show();
	}	
}
