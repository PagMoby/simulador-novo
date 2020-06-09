import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ISeller, Seller } from 'app/shared/model/seller.model';
import { SellerService } from './seller.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';

@Component({
  selector: 'jhi-seller-update',
  templateUrl: './seller-update.component.html',
})
export class SellerUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];

  editForm = this.fb.group({
    id: [],
    email: [],
    planoId: [],
  });

  constructor(
    protected sellerService: SellerService,
    protected planoService: PlanoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ seller }) => {
      this.updateForm(seller);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));
    });
  }

  updateForm(seller: ISeller): void {
    this.editForm.patchValue({
      id: seller.id,
      email: seller.email,
      planoId: seller.planoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const seller = this.createFromForm();
    if (seller.id !== undefined) {
      this.subscribeToSaveResponse(this.sellerService.update(seller));
    } else {
      this.subscribeToSaveResponse(this.sellerService.create(seller));
    }
  }

  private createFromForm(): ISeller {
    return {
      ...new Seller(),
      id: this.editForm.get(['id'])!.value,
      email: this.editForm.get(['email'])!.value,
      planoId: this.editForm.get(['planoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISeller>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IPlano): any {
    return item.id;
  }
}
