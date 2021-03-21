import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ILivro, Livro } from 'app/shared/model/livro.model';
import { LivroService } from './livro.service';
import { AlertError } from 'app/shared/alert/alert-error.model';

@Component({
  selector: 'jhi-livro-update',
  templateUrl: './livro-update.component.html',
})
export class LivroUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    isbn: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(13)]],
    titulo: [null, [Validators.required, Validators.maxLength(100)]],
    assunto: [],
    editora: [null, [Validators.required, Validators.minLength(4), Validators.maxLength(100)]],
    capa: [],
    capaContentType: [],
    numeroPaginas: [],
    tombo: [],
    copies: [null, [Validators.required]],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected livroService: LivroService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ livro }) => {
      this.updateForm(livro);
    });
  }

  updateForm(livro: ILivro): void {
    this.editForm.patchValue({
      id: livro.id,
      isbn: livro.isbn,
      titulo: livro.titulo,
      assunto: livro.assunto,
      editora: livro.editora,
      capa: livro.capa,
      capaContentType: livro.capaContentType,
      numeroPaginas: livro.numeroPaginas,
      tombo: livro.tombo,
      copies: livro.copies,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('jhipsterSampleApplicationApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const livro = this.createFromForm();
    if (livro.id !== undefined) {
      this.subscribeToSaveResponse(this.livroService.update(livro));
    } else {
      this.subscribeToSaveResponse(this.livroService.create(livro));
    }
  }

  private createFromForm(): ILivro {
    return {
      ...new Livro(),
      id: this.editForm.get(['id'])!.value,
      isbn: this.editForm.get(['isbn'])!.value,
      titulo: this.editForm.get(['titulo'])!.value,
      assunto: this.editForm.get(['assunto'])!.value,
      editora: this.editForm.get(['editora'])!.value,
      capaContentType: this.editForm.get(['capaContentType'])!.value,
      capa: this.editForm.get(['capa'])!.value,
      numeroPaginas: this.editForm.get(['numeroPaginas'])!.value,
      tombo: this.editForm.get(['tombo'])!.value,
      copies: this.editForm.get(['copies'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILivro>>): void {
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
