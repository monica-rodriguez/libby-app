import { render, screen, fireEvent } from '@testing-library/react';
import Lists from '../pages/Lists';
import BookCard from '../components/BookCard';

jest.mock('../components/BookCard', () => ({ book }) => <div>{book.title}</div>);

beforeEach(() => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () =>
        Promise.resolve({
          checkedOut: [{ id: 1, title: 'Checked Book', author: 'A' }],
          tbr: [{ id: 2, title: 'To Read Book', author: 'B' }],
          finished: [{ id: 3, title: 'Finished Book', author: 'C' }],
        }),
    })
  );
});

afterEach(() => {
  jest.resetAllMocks();
});

test('renders all tabs and books', async () => {
  render(<Lists />);

  expect(await screen.findByText('Checked Book')).toBeInTheDocument();

  fireEvent.click(screen.getByText('To Be Read'));
  expect(await screen.findByText('To Read Book')).toBeInTheDocument();

  fireEvent.click(screen.getByText('Finished'));
  expect(await screen.findByText('Finished Book')).toBeInTheDocument();
});
