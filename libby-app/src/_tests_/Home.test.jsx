import { render, screen } from '@testing-library/react';
import Home from '../pages/Home';

beforeEach(() => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () =>
        Promise.resolve([
          { id: 1, title: 'Book 1', author: 'Author A' },
          { id: 2, title: 'Book 2', author: 'Author B' },
        ]),
    })
  );
});

afterEach(() => {
  jest.resetAllMocks();
});

test('renders book list', async () => {
  render(<Home />);

  const mockBooks = [
    { id: 1, title: 'Book 1' },
    { id: 2, title: 'Book 2' },
  ];

  for (const book of mockBooks) {
    expect(await screen.findByText(book.title)).toBeInTheDocument();
  }
});
