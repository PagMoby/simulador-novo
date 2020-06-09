import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { SimuladorTestModule } from '../../../test.module';
import { JurosUpdateComponent } from 'app/entities/juros/juros-update.component';
import { JurosService } from 'app/entities/juros/juros.service';
import { Juros } from 'app/shared/model/juros.model';

describe('Component Tests', () => {
  describe('Juros Management Update Component', () => {
    let comp: JurosUpdateComponent;
    let fixture: ComponentFixture<JurosUpdateComponent>;
    let service: JurosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SimuladorTestModule],
        declarations: [JurosUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(JurosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(JurosUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(JurosService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Juros(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Juros();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
