import { render, screen } from '@testing-library/react';
import BookDetails from '../pages/BookDetails';
import { MemoryRouter, Route, Routes } from 'react-router-dom';

test('renders book detail info', async () => {
  const mockBook = { id: 1, title: 'Sample', author: 'Author', outline: 'Outline' };

  global.fetch = jest.fn(() =>
    Promise.resolve({ json: () => Promise.resolve(mockBook) })
  );

  render(
    <MemoryRouter initialEntries={['/book/1']}>
      <Routes>
        <Route path="/book/:id" element={<BookDetail />} />
      </Routes>
    </MemoryRouter>
  );

  expect(await screen.findByText(/Sample/i)).toBeInTheDocument();
  expect(screen.getByText(/Author/i)).toBeInTheDocument();
});
