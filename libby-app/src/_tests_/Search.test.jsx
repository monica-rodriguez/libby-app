import { render, screen, fireEvent } from '@testing-library/react';
import Search from '../pages/Search';
import BookCard from '../components/BookCard';

jest.mock('../components/BookCard', () => ({ book }) => <div>{book.title}</div>);

beforeEach(() => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () =>
        Promise.resolve([
          { id: 1, title: 'Starry Night', author: 'Van Gogh' },
          { id: 2, title: 'Mona Lisa', author: 'Da Vinci' },
        ]),
    })
  );
});

afterEach(() => {
  jest.resetAllMocks();
});

test('filters books based on query', async () => {
  render(<Search />);

  const input = screen.getByPlaceholderText('Search by title or author');
  fireEvent.change(input, { target: { value: 'Starry' } });

  expect(await screen.findByText('Starry Night')).toBeInTheDocument();
  expect(screen.queryByText('Mona Lisa')).toBeNull();
});
