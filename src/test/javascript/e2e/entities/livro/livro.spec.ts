import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LivroComponentsPage, LivroDeleteDialog, LivroUpdatePage } from './livro.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('Livro e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let livroComponentsPage: LivroComponentsPage;
  let livroUpdatePage: LivroUpdatePage;
  let livroDeleteDialog: LivroDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Livros', async () => {
    await navBarPage.goToEntity('livro');
    livroComponentsPage = new LivroComponentsPage();
    await browser.wait(ec.visibilityOf(livroComponentsPage.title), 5000);
    expect(await livroComponentsPage.getTitle()).to.eq('jhipsterSampleApplicationApp.livro.home.title');
    await browser.wait(ec.or(ec.visibilityOf(livroComponentsPage.entities), ec.visibilityOf(livroComponentsPage.noResult)), 1000);
  });

  it('should load create Livro page', async () => {
    await livroComponentsPage.clickOnCreateButton();
    livroUpdatePage = new LivroUpdatePage();
    expect(await livroUpdatePage.getPageTitle()).to.eq('jhipsterSampleApplicationApp.livro.home.createOrEditLabel');
    await livroUpdatePage.cancel();
  });

  it('should create and save Livros', async () => {
    const nbButtonsBeforeCreate = await livroComponentsPage.countDeleteButtons();

    await livroComponentsPage.clickOnCreateButton();

    await promise.all([
      livroUpdatePage.setIsbnInput('isbn'),
      livroUpdatePage.setTituloInput('titulo'),
      livroUpdatePage.setAssuntoInput('assunto'),
      livroUpdatePage.setEditoraInput('editora'),
      livroUpdatePage.setCapaInput(absolutePath),
      livroUpdatePage.setNumeroPaginasInput('5'),
      livroUpdatePage.setTomboInput('5'),
      livroUpdatePage.setCopiesInput('5'),
    ]);

    expect(await livroUpdatePage.getIsbnInput()).to.eq('isbn', 'Expected Isbn value to be equals to isbn');
    expect(await livroUpdatePage.getTituloInput()).to.eq('titulo', 'Expected Titulo value to be equals to titulo');
    expect(await livroUpdatePage.getAssuntoInput()).to.eq('assunto', 'Expected Assunto value to be equals to assunto');
    expect(await livroUpdatePage.getEditoraInput()).to.eq('editora', 'Expected Editora value to be equals to editora');
    expect(await livroUpdatePage.getCapaInput()).to.endsWith(fileNameToUpload, 'Expected Capa value to be end with ' + fileNameToUpload);
    expect(await livroUpdatePage.getNumeroPaginasInput()).to.eq('5', 'Expected numeroPaginas value to be equals to 5');
    expect(await livroUpdatePage.getTomboInput()).to.eq('5', 'Expected tombo value to be equals to 5');
    expect(await livroUpdatePage.getCopiesInput()).to.eq('5', 'Expected copies value to be equals to 5');

    await livroUpdatePage.save();
    expect(await livroUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await livroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Livro', async () => {
    const nbButtonsBeforeDelete = await livroComponentsPage.countDeleteButtons();
    await livroComponentsPage.clickOnLastDeleteButton();

    livroDeleteDialog = new LivroDeleteDialog();
    expect(await livroDeleteDialog.getDialogTitle()).to.eq('jhipsterSampleApplicationApp.livro.delete.question');
    await livroDeleteDialog.clickOnConfirmButton();

    expect(await livroComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
