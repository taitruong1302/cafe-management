import { Component, EventEmitter, Inject, OnInit } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { NgxUiLoaderService } from 'ngx-ui-loader';
import { CategoryService } from 'src/app/services/category.service';
import { ProductService } from 'src/app/services/product.service';
import { SnackbarService } from 'src/app/services/snackbar.service';
import { GlobalConstants } from 'src/app/shared/global-constants';

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.css']
})
export class ProductComponent implements OnInit {

  onAddProduct = new EventEmitter();
  onEditProduct = new EventEmitter();
  dialogAction: any = "Add";
  action: any = "Add";
  responseMessage: any;
  categories: any = [];
  productForm: FormGroup = new FormGroup({
    name: new FormControl(null, [Validators.required, Validators.pattern(GlobalConstants.nameRegex)]),
    categoryId: new FormControl(null, [Validators.required]),
    price: new FormControl(null, [Validators.required]),
    description: new FormControl(null, [Validators.required])
  })

  constructor(@Inject(MAT_DIALOG_DATA) public dialogData: any,
    private productService: ProductService,
    public dialogRef: MatDialogRef<ProductComponent>,
    private categoryService: CategoryService,
    private snackbarservice: SnackbarService,
    private ngxService: NgxUiLoaderService) { }

  ngOnInit(): void {
    if (this.dialogData.action === 'Edit') {
      this.dialogAction = "Edit";
      this.action = "Update";
      this.productForm.patchValue(this.dialogData.data);
    }

    this.getCategories();
  }

  getCategories() {
    this.categoryService.getCategories().subscribe((res: any) => {
      this.categories = res;
    }, error => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarservice.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  handleSubmit() {
    if (this.dialogAction === "Edit") {
      this.edit();
    }
    else {
      this.add();
    }
  }

  edit() {
    var formData = this.productForm.value;
    var data = {
      id: this.dialogData.data.id,
      name: formData.name,
      category_id: formData.categoryId,
      price: formData.price,
      description: formData.description
    }
    this.productService.updateProduct(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onEditProduct.emit();
      this.responseMessage = res.message;
      this.snackbarservice.openSnackBar(this.responseMessage, "success");
    }, error => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarservice.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  add() {
    var formData = this.productForm.value;
    var data = {
      name: formData.name,
      category_id: formData.categoryId,
      price: formData.price,
      description: formData.description
    }
    this.productService.addProduct(data).subscribe((res: any) => {
      this.dialogRef.close();
      this.onAddProduct.emit();
      this.responseMessage = res.message;
      this.snackbarservice.openSnackBar(this.responseMessage, "success");
    }, error => {
      console.log(error);
      if (error.error?.message) {
        this.responseMessage = error.error?.message;
      }
      else {
        this.responseMessage = GlobalConstants.genericError;
      }
      this.snackbarservice.openSnackBar(this.responseMessage, GlobalConstants.error);
    })
  }

  getControl(name: any): AbstractControl | null {
    return this.productForm.get(name)
  }
}
