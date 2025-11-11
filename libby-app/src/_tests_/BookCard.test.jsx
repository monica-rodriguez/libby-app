import { render, screen } from '@testing-library/react';
import BookCard from '../components/BookCard';

const sampleBook = {
  id: 1,
  title: 'Sample Book',
  author: 'Author Name',
};

test('renders book title and author', () => {
  render(<BookCard book={sampleBook} />);
  expect(screen.getByText(/Sample Book/i)).toBeInTheDocument();
  expect(screen.getByText(/Author Name/i)).toBeInTheDocument();
});
test('renders book card with correct data-testid', () => {
  render(<BookCard book={sampleBook} />);
  const bookCardElement = screen.getByTestId('book-card-1');
  expect(bookCardElement).toBeInTheDocument();
});