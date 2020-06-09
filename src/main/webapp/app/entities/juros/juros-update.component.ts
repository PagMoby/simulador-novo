import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IJuros, Juros } from 'app/shared/model/juros.model';
import { JurosService } from './juros.service';
import { IPlano } from 'app/shared/model/plano.model';
import { PlanoService } from 'app/entities/plano/plano.service';

@Component({
  selector: 'jhi-juros-update',
  templateUrl: './juros-update.component.html',
})
export class JurosUpdateComponent implements OnInit {
  isSaving = false;
  planos: IPlano[] = [];

  editForm = this.fb.group({
    id: [],
    juros: [],
    operacao: [],
    planoId: [],
  });

  constructor(
    protected jurosService: JurosService,
    protected planoService: PlanoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ juros }) => {
      this.updateForm(juros);

      this.planoService.query().subscribe((res: HttpResponse<IPlano[]>) => (this.planos = res.body || []));
    });
  }

  updateForm(juros: IJuros): void {
    this.editForm.patchValue({
      id: juros.id,
      juros: juros.juros,
      operacao: juros.operacao,
      planoId: juros.planoId,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const juros = this.createFromForm();
    if (juros.id !== undefined) {
      this.subscribeToSaveResponse(this.jurosService.update(juros));
    } else {
      this.subscribeToSaveResponse(this.jurosService.create(juros));
    }
  }

  private createFromForm(): IJuros {
    return {
      ...new Juros(),
      id: this.editForm.get(['id'])!.value,
      juros: this.editForm.get(['juros'])!.value,
      operacao: this.editForm.get(['operacao'])!.value,
      planoId: this.editForm.get(['planoId'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IJuros>>): void {
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
