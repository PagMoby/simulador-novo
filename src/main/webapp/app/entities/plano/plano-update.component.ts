import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPlano, Plano } from 'app/shared/model/plano.model';
import { PlanoService } from './plano.service';

@Component({
  selector: 'jhi-plano-update',
  templateUrl: './plano-update.component.html',
})
export class PlanoUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nome: [],
  });

  constructor(protected planoService: PlanoService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plano }) => {
      this.updateForm(plano);
    });
  }

  updateForm(plano: IPlano): void {
    this.editForm.patchValue({
      id: plano.id,
      nome: plano.nome,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plano = this.createFromForm();
    if (plano.id !== undefined) {
      this.subscribeToSaveResponse(this.planoService.update(plano));
    } else {
      this.subscribeToSaveResponse(this.planoService.create(plano));
    }
  }

  private createFromForm(): IPlano {
    return {
      ...new Plano(),
      id: this.editForm.get(['id'])!.value,
      nome: this.editForm.get(['nome'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlano>>): void {
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
}
