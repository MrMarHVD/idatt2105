import axiosInstance from '@/axiosConfig';

export async function fetchToken({ username, password }: { username: string, password: string }) {
  return await axiosInstance.post('/auth/token', { username, password });
}
