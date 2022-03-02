export async function FetchData(url, method, type, data) {
    if (method === 'GET') {
        const result = await fetch(url,
            {
                method: method,
            }
        );
        const json = await result.json();
        return json;
    }
}