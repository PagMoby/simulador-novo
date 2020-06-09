import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJuros } from 'app/shared/model/juros.model';

@Component({
  selector: 'jhi-juros-detail',
  templateUrl: './juros-detail.component.html',
})
export class JurosDetailComponent implements OnInit {
  juros: IJuros | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ juros }) => (this.juros = juros));
  }

  previousState(): void {
    window.history.back();
  }
}
