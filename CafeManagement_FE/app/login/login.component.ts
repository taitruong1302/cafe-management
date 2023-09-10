import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { Route, Router } from '@angular/router';
import { UserService } from '../services/user.service';
import { MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { SnackbarService } from '../services/snackbar.service';
import { GlobalConstants } from '../shared/global-constants';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  hide = true;

  loginForm: FormGroup = new FormGroup({
    email: new FormControl(null, [Validators.required, Validators.pattern(GlobalConstants.emailRegex)]),
    password: new FormControl(null, [Validators.required])
  });
  responseMessage: any;

  constructor(private router: Router,
    private userService: UserService,
    public dialogRef: MatDialogRef<LoginComponent>,
    private ngxServicee: NgxUiLoaderService,
    private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  handleSubmit() {
    this.ngxServicee.start();
    var formData = this.loginForm.value;
    var data = {
      email: formData.email,
      password: formData.password
    }
    this.userService.login(data).subscribe((res: any) => {
      this.ngxServicee.stop();
      this.dialogRef.close();
      localStorage.setItem('token', res.token);
      this.router.navigate(['/cafe/dashboard'])
    }, error => {
      this.ngxServicee.stop();
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    });
  }

  getControl(name: any): AbstractControl | null {
    return this.loginForm.get(name)
  }
}
