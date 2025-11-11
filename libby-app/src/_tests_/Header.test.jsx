import { render, screen, fireEvent } from '@testing-library/react';
import Header from '../components/Header';

beforeEach(() => {
  global.fetch = jest.fn(() =>
    Promise.resolve({
      json: () =>
        Promise.resolve([
          { id: 1, title: 'Starry Night', author: 'Vincent' },
          { id: 2, title: 'Folklore', author: 'Taylor' },
        ]),
    })
  );
});

afterEach(() => {
  jest.restoreAllMocks();
});


test('renders header title', () => {
  render(<Header activeTab="home" setActiveTab={() => {}} />);
  expect(screen.getByText(/Starlight Folklore/i)).toBeInTheDocument();
});

test('navigation buttons trigger setActiveTab', () => {
  const mockSetActiveTab = jest.fn();
  render(<Header activeTab="home" setActiveTab={mockSetActiveTab} />);
  
  fireEvent.click(screen.getByText(/Search/i));
  expect(mockSetActiveTab).toHaveBeenCalledWith('search');
});

