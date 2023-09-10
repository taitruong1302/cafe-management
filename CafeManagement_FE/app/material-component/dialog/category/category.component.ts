import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { CategoryService } from 'src/app/services/category.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {
  onAddCategory = new EventEmitter();
  onEditcategory = new EventEmitter();
  dialogAction: any = "Add";
  action: any = "Add";
  categoryForm: FormGroup = new FormGroup({
    name: new FormControl(null, [Validators.required])
  })
  responseMessage: any;

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private categoryService: CategoryService,
    public dialogRef: MatDialogRef<CategoryComponent>,
    private snackbarService: SnackbarService) { }

  ngOnInit(): void {
    if (this.dialogData.action === "Edit") {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.categoryForm.patchValue(this.dialogData.data);
    }
  }

  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    }
    else {
      this.add();
    }
  }

  add() {
    var formData = this.categoryForm.value;
    var data = {
      name: formData.name
    }
    this.categoryService.addCategory(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddCategory.emit();
      this.responseMessage = res.message;
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, error => {
      this.dialogRef.close();
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarService.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  edit() {
    var formData = this.categoryForm.value;
    var data = {
      id: this.dialogData.data.id,
      name: formData.name
    }
    this.categoryService.updateCategory(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddCategory.emit();
      this.responseMessage = res.message;
      this.snackbarService.openSnackBar(this.responseMessage, "success");
    }, error => {
      this.dialogRef.close();
      console.log(error);
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
    return this.categoryForm.get(name)
  }
}
