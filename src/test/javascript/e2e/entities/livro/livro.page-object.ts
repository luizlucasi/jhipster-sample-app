import { element, by, ElementFinder } from 'protractor';

export class LivroComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-livro div table .btn-danger'));
  title = element.all(by.css('jhi-livro div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class LivroUpdatePage {
  pageTitle = element(by.id('jhi-livro-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  isbnInput = element(by.id('field_isbn'));
  tituloInput = element(by.id('field_titulo'));
  assuntoInput = element(by.id('field_assunto'));
  editoraInput = element(by.id('field_editora'));
  capaInput = element(by.id('file_capa'));
  numeroPaginasInput = element(by.id('field_numeroPaginas'));
  tomboInput = element(by.id('field_tombo'));
  copiesInput = element(by.id('field_copies'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIsbnInput(isbn: string): Promise<void> {
    await this.isbnInput.sendKeys(isbn);
  }

  async getIsbnInput(): Promise<string> {
    return await this.isbnInput.getAttribute('value');
  }

  async setTituloInput(titulo: string): Promise<void> {
    await this.tituloInput.sendKeys(titulo);
  }

  async getTituloInput(): Promise<string> {
    return await this.tituloInput.getAttribute('value');
  }

  async setAssuntoInput(assunto: string): Promise<void> {
    await this.assuntoInput.sendKeys(assunto);
  }

  async getAssuntoInput(): Promise<string> {
    return await this.assuntoInput.getAttribute('value');
  }

  async setEditoraInput(editora: string): Promise<void> {
    await this.editoraInput.sendKeys(editora);
  }

  async getEditoraInput(): Promise<string> {
    return await this.editoraInput.getAttribute('value');
  }

  async setCapaInput(capa: string): Promise<void> {
    await this.capaInput.sendKeys(capa);
  }

  async getCapaInput(): Promise<string> {
    return await this.capaInput.getAttribute('value');
  }

  async setNumeroPaginasInput(numeroPaginas: string): Promise<void> {
    await this.numeroPaginasInput.sendKeys(numeroPaginas);
  }

  async getNumeroPaginasInput(): Promise<string> {
    return await this.numeroPaginasInput.getAttribute('value');
  }

  async setTomboInput(tombo: string): Promise<void> {
    await this.tomboInput.sendKeys(tombo);
  }

  async getTomboInput(): Promise<string> {
    return await this.tomboInput.getAttribute('value');
  }

  async setCopiesInput(copies: string): Promise<void> {
    await this.copiesInput.sendKeys(copies);
  }

  async getCopiesInput(): Promise<string> {
    return await this.copiesInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class LivroDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-livro-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-livro'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
