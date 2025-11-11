import { render, screen } from '@testing-library/react';
import Profile from '../pages/Profile';

test('renders profile info', () => {
  render(<Profile />);
  expect(screen.getByText(/Reader 1's Profile/i)).toBeInTheDocument();
  expect(screen.getByText(/Email: reader1@example.com/i)).toBeInTheDocument();
  expect(screen.getByText(/Checked Out: 3/i)).toBeInTheDocument();
});
