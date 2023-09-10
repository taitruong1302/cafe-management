import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { HomeComponent } from '../home/home.component';
import { AbstractControl, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { SnackbarService } from '../services/snackbar.service';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  password = true;
  confirmPassword = true;
  responseMessage: any;

  signupForm: FormGroup = new FormGroup({
    name: new FormControl("", [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]),
    email: new FormControl("", [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]),
    contactNumber: new FormControl("", [Validators.required, Validators.pattern(GlobalConstants.contactNumberRegex)]),
    password: new FormControl("", [Validators.required]),
    confirmPassword: new FormControl("", [Validators.required])
  })

  constructor(private formBuilder: FormBuilder,
    private router: Router,
    private userService: UserService,
    private snackbarService: SnackbarService,
    public dialogRef: MatDialogRef<SignupComponent>,
    private ngxService: NgxUiLoaderService) { }

  ngOnInit(): void {

  }

  getControl(name: any): AbstractControl | null {
    return this.signupForm.get(name)
  }

  validateSubmit() {
    if (this.signupForm.value.password != this.signupForm.value.confirmPassword) {
      return true;
    }
    else {
      return false;
    }
  }

  handleSubmit() {
    this.ngxService.start();
    var formData = this.signupForm.value;
    var data = {
      name: formData.name,
      email: formData.email,
      contactNumber: formData.contactNumber,
      password: formData.password
    }
    this.userService.signUp(data).subscribe((res: any) => {
      this.ngxService.stop();
      this.dialogRef.close();
      this.responseMessage = res.message;
      this.snackbarService.openSnackBar(this.responseMessage, "");
      this.router.navigate(['/']);

    }, error => {
      this.ngxService.stop();
      if (error.error?.message) {
        this.responseMessage = error.error?.message
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }
}
