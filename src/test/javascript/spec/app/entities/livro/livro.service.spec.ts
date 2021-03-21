import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { LivroService } from 'app/entities/livro/livro.service';
import { ILivro, Livro } from 'app/shared/model/livro.model';

describe('Service Tests', () => {
  describe('Livro Service', () => {
    let injector: TestBed;
    let service: LivroService;
    let httpMock: HttpTestingController;
    let elemDefault: ILivro;
    let expectedResult: ILivro | ILivro[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(LivroService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Livro(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 'image/png', 'AAAAAAA', 0, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Livro', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Livro()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Livro', () => {
        const returnedFromService = Object.assign(
          {
            isbn: 'BBBBBB',
            titulo: 'BBBBBB',
            assunto: 'BBBBBB',
            editora: 'BBBBBB',
            capa: 'BBBBBB',
            numeroPaginas: 1,
            tombo: 1,
            copies: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Livro', () => {
        const returnedFromService = Object.assign(
          {
            isbn: 'BBBBBB',
            titulo: 'BBBBBB',
            assunto: 'BBBBBB',
            editora: 'BBBBBB',
            capa: 'BBBBBB',
            numeroPaginas: 1,
            tombo: 1,
            copies: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Livro', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
