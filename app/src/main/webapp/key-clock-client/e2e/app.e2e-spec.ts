import { KeyClockClientPage } from './app.po';

describe('key-clock-client App', () => {
  let page: KeyClockClientPage;

  beforeEach(() => {
    page = new KeyClockClientPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
