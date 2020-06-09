import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJuros } from 'app/shared/model/juros.model';
import { JurosService } from './juros.service';

@Component({
  templateUrl: './juros-delete-dialog.component.html',
})
export class JurosDeleteDialogComponent {
  juros?: IJuros;

  constructor(protected jurosService: JurosService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.jurosService.delete(id).subscribe(() => {
      this.eventManager.broadcast('jurosListModification');
      this.activeModal.close();
    });
  }
}
