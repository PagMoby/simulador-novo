import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { JurosService } from './juros.service';

import { ITabela } from 'app/shared/model/tabela.model';

@Component({
  selector: 'jhi-juros',
  templateUrl: './simulador.component.html',
})
export class SimuladorComponent implements OnInit, OnDestroy {
  condicoes?: ITabela[];

  responsavelTaxa?: any;
  valorDaVenda?: any;
  page!: number;

  eventSubscriber?: Subscription;
  ngbPaginationPage = 1;

  constructor(
    protected jurosService: JurosService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected eventManager: JhiEventManager
  ) {}

  ngOnInit(): void {}

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  simular(): void {
    this.jurosService.simular(this.valorDaVenda, this.responsavelTaxa).subscribe(
      (res: HttpResponse<ITabela[]>) => this.onSuccess(res.body),
      () => this.onError()
    );
  }

  protected onSuccess(data: ITabela[] | null): void {
    this.condicoes = data || [];
  }

  protected onError(): void {
    this.ngbPaginationPage = this.page ?? 1;
  }
}
