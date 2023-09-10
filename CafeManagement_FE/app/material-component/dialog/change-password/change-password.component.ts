import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { UserService } from 'src/app/services/user.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent {
  oldPassword = true;
  newPassword = true;
  confirmPassword = true;
  changePasswordForm: FormGroup = new FormGroup({
    oldPassword: new FormControl(null, [Validators.required]),
    newPassword: new FormControl(null, [Validators.required]),
    confirmPassword: new FormControl(null, [Validators.required])
  })
  responseMessage: any;

  constructor(private userService: UserService,
    public dialogRef: MatDialogRef<ChangePasswordComponent>,
    private ngxService: NgxUiLoaderService,
    private snackbarService: SnackbarService) { }

  validateSubmit() {
    if (this.getControl('newPassword')?.value != this.getControl('confirmPassword')?.value) {
      return true;
    }
    return false;
  }

  handlePasswordChangeSubmit() {
    this.ngxService.start();
    var formData = this.changePasswordForm.value;
    var data = {
      oldPassword: formData.oldPassword,
      newPassword: formData.newPassword,
      confirmPassword: formData.confirmPassword
    }

    this.userService.changePassword(data).subscribe((res: any) => {
      this.ngxService.stop();
      this.responseMessage = res?.message;
      this.dialogRef.close();
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, error => {
      console.log(error);
      this.ngxService.stop();
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  getControl(name: any): AbstractControl | null {
    return this.changePasswordForm.get(name)
  }
}
